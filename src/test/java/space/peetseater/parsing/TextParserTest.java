package space.peetseater.parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.TextNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.TextToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextParserTest {

    TextParser textParser;

    @BeforeEach
    public void setup() {
        textParser = new TextParser();
    }

    @Test
    public void returns_null_node_if_empty_args() {
        TokenList tokens = new TokenList(List.of());
        AbstractMarkdownNode node = textParser.match(tokens);
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void returns_a_text_node_if_text_token_first_in_list() {
        TokenList tokens = new TokenList(List.of(new TextToken("bla bla")));
        AbstractMarkdownNode node = textParser.match(tokens);
        assertEquals(TextNode.TYPE, node.getType());
        assertEquals(1, node.getConsumed());
    }

    @Test
    public void returns_a_null_node_if_text_token_not_first_in_list() {
        TokenList tokens = new TokenList(List.of());
        AbstractMarkdownNode node = textParser.match(tokens);
        assertEquals(NullNode.INSTANCE, node);
    }

}