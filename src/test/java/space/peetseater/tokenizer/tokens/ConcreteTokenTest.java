package space.peetseater.tokenizer.tokens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteTokenTest {

    @Test
    public void cannot_create_concrete_token_with_null_type() {
        assertThrows(AssertionError.class, () -> new ConcreteToken(null, "stuff"));
    }

    @Test
    public void cannot_create_concrete_token_with_null_value() {
        assertThrows(AssertionError.class, () -> new ConcreteToken("type", null));
    }

}