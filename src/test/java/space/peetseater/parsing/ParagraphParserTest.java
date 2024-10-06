package space.peetseater.parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.ParagraphNode;
import space.peetseater.parsing.ast.TextNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.EndOfFileToken;
import space.peetseater.tokenizer.tokens.NewLineToken;
import space.peetseater.tokenizer.tokens.TextToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParagraphParserTest {
    ParagraphParser paragraphParser;

    @BeforeEach
    public void setup() {
        paragraphParser = new ParagraphParser();
    }

    @Test
    public void returns_null_on_empty_input() {
        TokenList tokenList = new TokenList(List.of());
        AbstractMarkdownNode node = paragraphParser.match(tokenList);
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void returns_paragraph_on_sentence_and_two_newlines() {
        TokenList tokenList = new TokenList(List.of(
                new TextToken("Sentence!"),
                NewLineToken.INSTANCE,
                NewLineToken.INSTANCE
        ));
        AbstractMarkdownNode node = paragraphParser.match(tokenList);
        assertInstanceOf(ParagraphNode.class, node);
        ParagraphNode p = (ParagraphNode) node;
        assertEquals(3, node.getConsumed());
        assertEquals(1, p.getSentences().size());
        assertEquals("Sentence!", ((TextNode)p.getSentences().get(0)).getValue());
    }

    @Test
    public void returns_paragraph_with_multiple_sentences_and_two_newlines() {
        TokenList tokenList = new TokenList(List.of(
                new TextToken("Sentence!"),
                new TextToken("Sentence 2!"),
                NewLineToken.INSTANCE,
                NewLineToken.INSTANCE
        ));
        AbstractMarkdownNode node = paragraphParser.match(tokenList);
        assertInstanceOf(ParagraphNode.class, node);
        ParagraphNode p = (ParagraphNode) node;
        assertEquals(4, node.getConsumed());
        assertEquals(2, p.getSentences().size());
        assertEquals("Sentence!", ((TextNode)p.getSentences().get(0)).getValue());
        assertEquals("Sentence 2!", ((TextNode)p.getSentences().get(1)).getValue());
    }

    @Test
    public void returns_paragraph_if_file_ends_after_sentence_and_newline() {
        TokenList tokenList = new TokenList(List.of(
                new TextToken("Sentence!"),
                NewLineToken.INSTANCE,
                EndOfFileToken.INSTANCE
        ));
        AbstractMarkdownNode node = paragraphParser.match(tokenList);
        assertInstanceOf(ParagraphNode.class, node);
        ParagraphNode p = (ParagraphNode) node;
        assertEquals(3, node.getConsumed());
        assertEquals(1, p.getSentences().size());
        assertEquals("Sentence!", ((TextNode)p.getSentences().get(0)).getValue());
    }

    @Test
    public void returns_paragraph_if_file_ends_after_sentence() {
        TokenList tokenList = new TokenList(List.of(
                new TextToken("Sentence!"),
                EndOfFileToken.INSTANCE
        ));
        AbstractMarkdownNode node = paragraphParser.match(tokenList);
        assertInstanceOf(ParagraphNode.class, node);
        ParagraphNode p = (ParagraphNode) node;
        assertEquals(2, node.getConsumed());
        assertEquals(1, p.getSentences().size());
        assertEquals("Sentence!", ((TextNode)p.getSentences().get(0)).getValue());
    }

}