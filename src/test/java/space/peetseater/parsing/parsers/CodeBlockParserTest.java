package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.CodeBlockNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CodeBlockParserTest {

    CodeBlockParser codeBlockParser;
    @BeforeEach
    public void setup() {
        codeBlockParser = new CodeBlockParser();
    }

    @Test
    public void less_than_three_backticks_does_not_start_a_code_block() {
        TokenList tokens = new TokenList(List.of(
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE,
            new TextToken("wont be parsed"),
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE
        ));
        AbstractMarkdownNode node = codeBlockParser.match(tokens);
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void three_backticks_not_matched_by_three_will_not_start_a_code_block() {
        TokenList tokens = new TokenList(List.of(
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE,
            new TextToken("wont be parsed"),
            BacktickToken.INSTANCE
        ));
        AbstractMarkdownNode node = codeBlockParser.match(tokens);
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void three_backticks_matched_with_three_will_convert_the_in_between_to_code() {
        TokenList tokens = new TokenList(List.of(
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE,
            new TextToken("wont be parsed"),
            AngleStartToken.INSTANCE,
            AngleStopToken.INSTANCE,
            ColonToken.INSTANCE,
            DashToken.INSTANCE,
            EqualsToken.INSTANCE,
            NewLineToken.INSTANCE,
            ParenStopToken.INSTANCE,
            ParenStartToken.INSTANCE,
            PoundToken.INSTANCE,
            StarToken.INSTANCE,
            UnderscoreToken.INSTANCE,
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE,
            BacktickToken.INSTANCE
        ));
        AbstractMarkdownNode node = codeBlockParser.match(tokens);
        assertInstanceOf(CodeBlockNode.class, node);
        assertEquals("wont be parsed<>:-=\n)(#*_", ((CodeBlockNode) node).getValue());
    }
}