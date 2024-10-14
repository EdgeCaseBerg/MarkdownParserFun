package space.peetseater.tokenizer.scanners;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.NullToken;

import static org.junit.jupiter.api.Assertions.*;

class TextScannerTest {
    TextScanner textScanner;
    @BeforeEach
    public void setUp() {
         textScanner = new TextScanner();
    }

    @Test
    public void does_not_consume_tokens_simple_scanner_can_consume() {
        AbstractToken token = textScanner.fromString("This text consumed, but *not this text*");
        assertEquals("TEXT", token.getType());
        assertEquals("This text consumed, but ", token.getValue());
    }

    @Test
    public void returns_null_token_if_empty_input() {
        AbstractToken token = textScanner.fromString("");
        assertEquals(NullToken.INSTANCE, token);
    }

    @Test
    public void returns_null_token_if_no_match_at_start_of_input() {
        AbstractToken token = textScanner.fromString("_this wont be consumed_");
        assertEquals(NullToken.INSTANCE, token);
    }

}