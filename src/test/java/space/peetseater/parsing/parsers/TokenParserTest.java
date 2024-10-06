package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.BoldNode;
import space.peetseater.parsing.ast.ItalicsNode;
import space.peetseater.parsing.ast.TextNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.ConcreteToken;
import space.peetseater.tokenizer.tokens.StarToken;
import space.peetseater.tokenizer.tokens.TextToken;
import space.peetseater.tokenizer.tokens.UnderscoreToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenParserTest {

    TokenParser testParser;

    @BeforeEach
    public void setup() {
        testParser = new TokenParser() {
            @Override
            public AbstractMarkdownNode match(TokenList tokenList) {
                // We don't need a real implementantion because this test suite is just checking out the match* methods
                return null;
            }
        };
    }

    @Test
    public void match_zero_or_more_should_consume_input_until_no_match_found() {
        TokenList tokens = new TokenList(
          List.of(
                  new TextToken("This is part "),
                  UnderscoreToken.INSTANCE,
                  new TextToken("of an"),
                  UnderscoreToken.INSTANCE,
                  new TextToken("overall "),
                  StarToken.INSTANCE,
                  new TextToken("sentence "),
                  StarToken.INSTANCE,
                  new TextToken("so it should be all consumed")
          )
        );
        MatchedAndConsumed matched = testParser.matchZeroOrMore(tokens, new SentenceParser());
        assertEquals(9, matched.getConsumed());
        List<AbstractMarkdownNode> matchedNodes = matched.getMatched();
        assertInstanceOf(TextNode.class, matchedNodes.get(0));
        assertInstanceOf(ItalicsNode.class, matchedNodes.get(1));
        assertInstanceOf(TextNode.class, matchedNodes.get(2));
        assertInstanceOf(BoldNode.class, matchedNodes.get(3));
        assertInstanceOf(TextNode.class, matchedNodes.get(4));
    }
}