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

    public void assertTypeAndValueOf(AbstractMarkdownNode node, Class<? extends MarkdownNode> expectedClass, String expectedValue) {
        assertInstanceOf(expectedClass, node);
        MarkdownNode realNode = expectedClass.cast(node);
        assertEquals(expectedValue, realNode.getValue());
    }

    @BeforeEach
    public void setup() {
        tokenizer = new Tokenizer();
        markdownParser = new MarkdownParser();
    }

    @Test
    public void sample_text_from_ruby_blog_post() {
        String sampleFromRubyBlog = "__Foo__ and *text*.\n\nAnother para.";
        TokenList tokens = tokenizer.tokenize(sampleFromRubyBlog);
        AbstractMarkdownNode dom = markdownParser.parse(tokens);
        assertInstanceOf(BodyNode.class, dom);
        BodyNode root = (BodyNode) dom;

        AbstractMarkdownNode firstP = root.getBodyParts().get(0);
        assertInstanceOf(ParagraphNode.class, firstP);
        ParagraphNode p1 = (ParagraphNode) firstP;
        assertTypeAndValueOf(p1.getSentences().get(0), ItalicsNode.class, "Foo");
        assertTypeAndValueOf(p1.getSentences().get(1), TextNode.class, " and ");
        assertTypeAndValueOf(p1.getSentences().get(2), BoldNode.class, "text");

        AbstractMarkdownNode secondP = root.getBodyParts().get(1);
        assertInstanceOf(ParagraphNode.class, secondP);
        ParagraphNode p2 = (ParagraphNode) secondP;
        assertTypeAndValueOf(p2.getSentences().get(0), TextNode.class, "Another para.");
    }

    @Test
    public void sample_flattened_test() {
        String sampleFromRubyBlog = "__Foo__ and *text*.\n\nAnother para.";
        TokenList tokens = tokenizer.tokenize(sampleFromRubyBlog);
        AbstractMarkdownNode dom = markdownParser.parse(tokens);
        Flattener flattener = new Flattener();
        dom.accept(flattener);
        LinkedList<AbstractMarkdownNode> nodes = flattener.getNodes();
        assertTypeAndValueOf(nodes.get(0), ItalicsNode.class, "Foo");
        assertTypeAndValueOf(nodes.get(1), TextNode.class, " and ");
        assertTypeAndValueOf(nodes.get(2), BoldNode.class, "text");
        assertTypeAndValueOf(nodes.get(3), TextNode.class, ".");
        assertTypeAndValueOf(nodes.get(4), TextNode.class, "Another para.");
        assertEquals(2, flattener.getBodyCounts());
        assertEquals(0, flattener.getListCounts());
        assertEquals(2, flattener.getParagraphCounts());
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
                    new NodeAndValue(ItalicsNode.class, "Foo"),
                    new NodeAndValue(TextNode.class, " and "),
                    new NodeAndValue(BoldNode.class, "text"),
                    new NodeAndValue(TextNode.class, "."),
                    new NodeAndValue(TextNode.class, "Another para.")
                )
            )
        );
    }

    private record NodeAndValue(Class<? extends MarkdownNode> clazz, String value) {}
}
