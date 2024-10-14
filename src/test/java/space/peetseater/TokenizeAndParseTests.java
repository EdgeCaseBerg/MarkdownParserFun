package space.peetseater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import space.peetseater.generators.Flattener;
import space.peetseater.parsing.MarkdownParser;
import space.peetseater.parsing.ast.*;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.Tokenizer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizeAndParseTests {

    private Tokenizer tokenizer;
    private MarkdownParser markdownParser;

    @BeforeEach
    public void setup() {
        tokenizer = new Tokenizer();
        markdownParser = new MarkdownParser();
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void verify_sample_text_becomes_nodes(
        int expectedBodyCounts,
        int expectedListCounts,
        int expectedParagraphCounts,
        String sampleText,
        List<NodeAndValue> nodesToValue
    ) {
        TokenList tokens = tokenizer.tokenize(sampleText);
        AbstractMarkdownNode dom = markdownParser.parse(tokens);
        Flattener flattener = new Flattener();
        dom.accept(flattener);
        LinkedList<AbstractMarkdownNode> nodes = flattener.getNodes();
        Iterator<NodeAndValue> iter = nodesToValue.iterator();
        for (AbstractMarkdownNode node : nodes) {
            NodeAndValue expected = iter.next();
            assertTypeAndValueOf(node, expected.clazz, expected.value);
        }
        assertEquals(expectedBodyCounts, flattener.getBodyCounts());
        assertEquals(expectedListCounts, flattener.getListCounts());
        assertEquals(expectedParagraphCounts, flattener.getParagraphCounts());
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
            Arguments.of(2, 0, 2, "__Foo__ and *text*.\n\nAnother para.",
                List.of(
                    NV(ItalicsNode.class, "Foo"),
                    NV(TextNode.class, " and "),
                    NV(BoldNode.class, "text"),
                    NV(TextNode.class, "."),
                    NV(TextNode.class, "Another para.")
                )
            ),
            Arguments.of(3,0,3, "Hello there\n\nParagraph\n\nThis is _well formed_ markdown. *I guess*\n\n",
                List.of(
                    NV(TextNode.class, "Hello there"),
                    NV(TextNode.class, "Paragraph"),
                    NV(TextNode.class, "This is "),
                    NV(ItalicsNode.class, "well formed"),
                    NV(TextNode.class, " markdown. "),
                    NV(BoldNode.class, "I guess")
                )
            )
        );
    }

    public void assertTypeAndValueOf(AbstractMarkdownNode node, Class<? extends MarkdownNode> expectedClass, String expectedValue) {
        assertInstanceOf(expectedClass, node);
        MarkdownNode realNode = expectedClass.cast(node);
        assertEquals(expectedValue, realNode.getValue());
    }

    public static NodeAndValue NV(Class<? extends  MarkdownNode> clazz, String value) {
        return new NodeAndValue(clazz, value);
    }

    // Wish we had a plain old tuple class.
    public record NodeAndValue(Class<? extends MarkdownNode> clazz, String value) {}
}
