package space.peetseater.tokenizer;

import org.junit.jupiter.api.Test;
import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.NullToken;

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
        assertEquals("STAR", token.getType());
        assertEquals("*", token.getValue());
    }

    @Test
    public void newline_tokenize_to_newline_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("\nnewline");
        assertEquals("NEWLINE", token.getType());
        assertEquals("\n", token.getValue());
    }

    @Test
    public void underscore_tokenize_to_underscore_type_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("_italics_");
        assertEquals( "UNDERSCORE", token.getType());
        assertEquals( "_", token.getValue());
    }




}