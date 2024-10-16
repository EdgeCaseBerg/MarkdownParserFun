package space.peetseater.tokenizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Text;
import space.peetseater.tokenizer.tokens.EndOfFileToken;
import space.peetseater.tokenizer.tokens.NewLineToken;
import space.peetseater.tokenizer.tokens.PoundToken;
import space.peetseater.tokenizer.tokens.TextToken;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    Tokenizer tokenizer;
    @BeforeEach
    public void setup() {
        tokenizer = new Tokenizer();
    }

    @Test
    public void assign_pound_as_a_token_to_pounds_at_the_start_of_a_line() {
        TokenList tokens = tokenizer.tokenize("# Header");
        assertTrue(tokens.typesAheadAre(PoundToken.TYPE, TextToken.TYPE));
    }

    @Test
    public void assign_pound_as_a_token_to_pounds_after_a_newline() {
        TokenList tokens = tokenizer.tokenize("\n# Header");
        assertTrue(tokens.typesAheadAre(NewLineToken.TYPE, PoundToken.TYPE, TextToken.TYPE));
    }

    @Test
    public void treat_token_as_text_in_middle_of_sentences() {
        TokenList tokens = tokenizer.tokenize("This is not a # Header");
        assertTrue(tokens.typesAheadAre(TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, EndOfFileToken.TYPE));
        assertEquals("This is not a ", ((TextToken)tokens.get(0)).getValue());
        assertEquals("#", ((TextToken)tokens.get(1)).getValue());
        assertEquals(" Header", ((TextToken)tokens.get(2)).getValue());
    }

}