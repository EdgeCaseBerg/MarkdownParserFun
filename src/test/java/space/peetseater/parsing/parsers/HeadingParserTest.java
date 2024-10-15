package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.HeadingNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.TextNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.NewLineToken;
import space.peetseater.tokenizer.tokens.PoundToken;
import space.peetseater.tokenizer.tokens.TextToken;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HeadingParserTest {

    HeadingParser headingParser;

    @BeforeEach
    void setup() {
         headingParser = new HeadingParser();
    }

    @Test
    void parses_lines_not_starting_with_pound_sign_as_null() {
        AbstractMarkdownNode node = headingParser.match(new TokenList(List.of(
            new TextToken("blablabla")
        )));
        assertEquals(NullNode.INSTANCE, node);
    }

    @ParameterizedTest
    @MethodSource("listLevelTestInputs")
    void parse_level_of_header_properly(List<AbstractToken> inputTokens, int expectedLevel, int expectedConsumed, List<AbstractMarkdownNode> expectedChildren) {
        AbstractMarkdownNode node = headingParser.match(new TokenList(inputTokens));
        assertInstanceOf(HeadingNode.class, node);
        HeadingNode header = (HeadingNode) node;
        assertEquals(expectedLevel, header.getLevel());
        assertEquals(header.getConsumed(), expectedConsumed);
        Iterator<AbstractMarkdownNode> expected = expectedChildren.iterator();
        for (AbstractMarkdownNode actual : header.getChildren()) {
            assertEquals(expected.next(), actual);
        }
    }

    public static Stream<Arguments> listLevelTestInputs() {
        return Stream.of(
            Arguments.of(
                List.of(PoundToken.INSTANCE, new TextToken("header text"), NewLineToken.INSTANCE),
                1, 3,
                List.of(new TextNode("header text", 1))
            ),
            Arguments.of(
                List.of(PoundToken.INSTANCE, PoundToken.INSTANCE, new TextToken("header text"), NewLineToken.INSTANCE),
                2, 4,
                List.of(new TextNode("header text", 1))
            ),
            Arguments.of(
                List.of(PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, new TextToken("header text"), NewLineToken.INSTANCE),
                3, 5,
                List.of(new TextNode("header text", 1))
            ),
            Arguments.of(
                List.of(PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, new TextToken("header text"), NewLineToken.INSTANCE),
                4, 6,
                List.of(new TextNode("header text", 1))
            ),
            Arguments.of(
                List.of(PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, new TextToken("header text"), NewLineToken.INSTANCE),
                5, 7,
                List.of(new TextNode("header text", 1))
            ),
            Arguments.of(
                List.of(PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, PoundToken.INSTANCE, new TextToken("header text"), NewLineToken.INSTANCE),
                6, 8,
                List.of(new TextNode("header text", 1))
            )

        );
    }

}