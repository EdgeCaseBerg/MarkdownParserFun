package space.peetseater.tokenizer;

import space.peetseater.tokenizer.tokens.AbstractToken;

public interface TokenScanner {
    AbstractToken fromString(String input);
}
