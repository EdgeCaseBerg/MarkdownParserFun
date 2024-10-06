package space.peetseater.parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.BoldNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.StarToken;
import space.peetseater.tokenizer.tokens.TextToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoldParserTest {

    BoldParser boldParser;

    @BeforeEach
    public void setup() {
        boldParser = new BoldParser();
    }


    @Test
    public void empty_list_returns_null_node() {
        TokenList tokens = new TokenList(List.of());
        AbstractMarkdownNode node = boldParser.match(tokens);
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void matches_star_star_text_star_star_as_bold() {
        TokenList tokens = new TokenList(List.of(
                StarToken.INSTANCE,
                StarToken.INSTANCE,
                new TextToken("this text!"),
                StarToken.INSTANCE,
                StarToken.INSTANCE
        ));
        AbstractMarkdownNode node = boldParser.match(tokens);
        assertEquals(BoldNode.TYPE, node.getType());
        assertEquals(5, node.getConsumed());
        assertEquals("this text!", ((BoldNode) node).getValue() );
    }

    @Test
    public void matches_star_text_star_as_bold() {
        TokenList tokens = new TokenList(List.of(
                StarToken.INSTANCE,
                new TextToken("this text!"),
                StarToken.INSTANCE
        ));
        AbstractMarkdownNode node = boldParser.match(tokens);
        assertEquals(BoldNode.TYPE, node.getType());
        assertEquals(3, node.getConsumed());
        assertEquals("this text!", ((BoldNode) node).getValue() );
    }

}