package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.ItalicsNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.TextToken;
import space.peetseater.tokenizer.tokens.UnderscoreToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItalicsParserTest {

    ItalicsParser italicsParser;

    @BeforeEach
    public void setup() {
        italicsParser = new ItalicsParser();
    }


    @Test
    public void empty_list_returns_null_node() {
        TokenList tokens = new TokenList(List.of());
        AbstractMarkdownNode node = italicsParser.match(tokens);
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void matches_star_star_text_star_star_as_bold() {
        TokenList tokens = new TokenList(List.of(
                UnderscoreToken.INSTANCE,
                UnderscoreToken.INSTANCE,
                new TextToken("this text!"),
                UnderscoreToken.INSTANCE,
                UnderscoreToken.INSTANCE
        ));
        AbstractMarkdownNode node = italicsParser.match(tokens);
        assertEquals(ItalicsNode.TYPE, node.getType());
        assertEquals(5, node.getConsumed());
        assertEquals("this text!", ((ItalicsNode) node).getValue() );
    }

    @Test
    public void matches_star_text_star_as_bold() {
        TokenList tokens = new TokenList(List.of(
                UnderscoreToken.INSTANCE,
                new TextToken("this text!"),
                UnderscoreToken.INSTANCE
        ));
        AbstractMarkdownNode node = italicsParser.match(tokens);
        assertEquals(ItalicsNode.TYPE, node.getType());
        assertEquals(3, node.getConsumed());
        assertEquals("this text!", ((ItalicsNode) node).getValue() );
    }

}