package space.peetseater.tokenizer;

import org.junit.jupiter.api.Test;
import space.peetseater.tokenizer.tokens.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleScannerTest {

    @Test
    public void empty_strings_tokenize_to_null_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("");
        assertEquals(NullToken.INSTANCE, token);
    }

    @Test
    public void strings_not_matching_known_symbols_tokenize_to_null_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("foo bar baz");
        assertEquals(NullToken.INSTANCE, token);
    }


    @Test
    public void asterisks_tokenize_to_star_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("*bold*");
        assertEquals(StarToken.INSTANCE, token);
    }

    @Test
    public void newline_tokenize_to_newline_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("\nnewline");
        assertEquals(NewLineToken.INSTANCE, token);
    }

    @Test
    public void underscore_tokenize_to_underscore_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("_italics_");
        assertEquals(UnderscoreToken.INSTANCE, token);
    }

    @Test
    public void dash_tokenizes_to_dash_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("- blabla");
        assertEquals(DashToken.INSTANCE, token);
    }

    @Test
    public void bracket_start_tokenizes_to_bracket_start_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("[foo]");
        assertEquals(BracketStartToken.INSTANCE, token);
    }

    @Test
    public void bracket_end_tokenizes_to_bracket_end_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("]:https://blablabla");
        assertEquals(BracketEndToken.INSTANCE, token);
    }

    @Test
    public void paren_start_tokenizes_to_paren_start_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("(https://blabla)");
        assertEquals(ParenStartToken.INSTANCE, token);
    }

    @Test
    public void paren_end_tokenizes_to_paren_end_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString(")\n some text");
        assertEquals(ParenStopToken.INSTANCE, token);
    }




}