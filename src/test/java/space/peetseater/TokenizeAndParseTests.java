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
import space.peetseater.tokenizer.tokens.NewLineToken;

import java.util.Collection;
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
            ),
            Arguments.of(1, 0, 1, "\nHello *world*\nHow _are_ you?\n\n",
                List.of(
                    NV(TextNode.class, "Hello "),
                    NV(BoldNode.class, "world"),
                    NV(TextNode.class, "How "),
                    NV(ItalicsNode.class, "are"),
                    NV(TextNode.class, " you?")
                )
            ),
            Arguments.of(2, 1, 1, "A list\n\n- a list item\n- a list item again\n\n",
                List.of(
                    NV(TextNode.class,"A list"),
                    NV(TextNode.class, " a list item"),
                    NV(TextNode.class, " a list item again")
                )
            ),
            Arguments.of(1, 1, 0, " - but what about\n - with space at the start\n\n",
                List.of(
                    NV(TextNode.class, " but what about"),
                    NV(TextNode.class, " with space at the start")
                )
            ),
            Arguments.of(2,0, 1, "```\nHello there<ignoredtag>yay</ignoredtag>```\n",
                List.of(
                    NV(CodeBlockNode.class, "\nHello there<ignoredtag>yay</ignoredtag>"),
                    NV(TextNode.class, "\n")
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

    @Test
    public void parse_headers_as_part_of_body() {
        String sample = "#Heading 1\n##Heading 2\n\n### Heading 3\n\nParagraph\n\n#### Heading 4 # pound sign";
        TokenList tokens = tokenizer.tokenize(sample);
        AbstractMarkdownNode dom = markdownParser.parse(tokens);
        Flattener flattener = new Flattener();
        dom.accept(flattener);
        LinkedList<AbstractMarkdownNode> nodes = flattener.getNodes();
        List<AbstractMarkdownNode> expected = List.of(
            new HeadingNode(List.of(new TextNode("Heading 1", 1)), 1, 1),
            new HeadingNode(List.of(new TextNode("Heading 2", 1)), 2, 1),
            new HeadingNode(List.of(new TextNode(" Heading 3", 1)), 3, 1),
            new TextNode("Paragraph", 1),
            new HeadingNode(
                List.of(
                    new TextNode(" Heading 4 ", 1),
                    new TextNode("#", 1),
                    new TextNode(" pound sign", 1)
            ), 4, 1)
        );
        Iterator<AbstractMarkdownNode> actual = nodes.iterator();
        for (AbstractMarkdownNode node : expected) {
            AbstractMarkdownNode actualNode = actual.next();
            assertInstanceOf(node.getClass(), actualNode);
            if (actualNode instanceof HeadingNode headNode) {
                List<AbstractMarkdownNode> actualChildren = headNode.getChildren();
                List<AbstractMarkdownNode> expectedChildren = ((HeadingNode)node).getChildren();
                Iterator<AbstractMarkdownNode> childIter = actualChildren.iterator();
                for (AbstractMarkdownNode expectedChild : expectedChildren) {
                    AbstractMarkdownNode actualNext = childIter.next();
                    assertInstanceOf(expectedChild.getClass(), actualNext);
                    if (actualNext instanceof TextNode textNode) {
                        assertEquals(((TextNode)expectedChild).getValue(), textNode.getValue());
                    }
                }
            }
        }
        assertEquals(4, flattener.getHeadingCounts());
    }


}
