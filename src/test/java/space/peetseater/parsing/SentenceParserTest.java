package space.peetseater.parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.*;
import space.peetseater.parsing.parsers.SentenceParser;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.StarToken;
import space.peetseater.tokenizer.tokens.TextToken;
import space.peetseater.tokenizer.tokens.UnderscoreToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SentenceParserTest {
    SentenceParser sentenceParser;

    @BeforeEach
    public void setup() {
        sentenceParser = new SentenceParser();
    }

    @Test
    public void return_null_node_on_no_tokens() {
        AbstractMarkdownNode node = sentenceParser.match(new TokenList(List.of()));
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void should_parse_text_token_as_sentence() {
        AbstractMarkdownNode node = sentenceParser.match(new TokenList(List.of(new TextToken("hi"))));
        assertInstanceOf(TextNode.class, node);
        assertEquals(1, node.getConsumed());
        assertEquals("hi", ((TextNode) node).getValue());
    }

    @Test
    public void should_parse_bold_token_as_sentence() {
        AbstractMarkdownNode node = sentenceParser.match(new TokenList(
                List.of(
                        StarToken.INSTANCE,
                        new TextToken("Hi"),
                        StarToken.INSTANCE
                )
        ));
        assertInstanceOf(BoldNode.class, node);
        assertEquals(3, node.getConsumed());
        assertEquals("Hi", ((BoldNode) node).getValue());
    }

    @Test
    public void should_parse_italics_token_as_sentence() {
        AbstractMarkdownNode node = sentenceParser.match(new TokenList(List.of(
                UnderscoreToken.INSTANCE,
                new TextToken("Hello"),
                UnderscoreToken.INSTANCE
            )
        ));
        assertInstanceOf(ItalicsNode.class, node);
        assertEquals("Hello", ((ItalicsNode) node).getValue());
        assertEquals(3, node.getConsumed());
    }
}