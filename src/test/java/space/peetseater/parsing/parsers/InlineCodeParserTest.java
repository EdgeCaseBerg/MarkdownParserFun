package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.InlineCodeNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.BacktickToken;
import space.peetseater.tokenizer.tokens.TextToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InlineCodeParserTest {

    InlineCodeParser inlineCodeParser;
    @BeforeEach
    public void setup() {
        inlineCodeParser = new InlineCodeParser();
    }

    @Test
    public void return_an_inline_code_node_if_backticks_wrap_text() {
        TokenList tokens = new TokenList(List.of(
            BacktickToken.INSTANCE,
            new TextToken("I am code"),
            BacktickToken.INSTANCE
        ));
        AbstractMarkdownNode node = inlineCodeParser.match(tokens);
        assertInstanceOf(InlineCodeNode.class, node);
        InlineCodeNode inlineCodeNode = (InlineCodeNode) node;
        assertEquals("I am code", inlineCodeNode.getValue());
    }
}