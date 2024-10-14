package space.peetseater.tokenizer.scanners;

import space.peetseater.tokenizer.tokens.AbstractToken;

public interface TokenScanner {
    AbstractToken fromString(String input);
}
