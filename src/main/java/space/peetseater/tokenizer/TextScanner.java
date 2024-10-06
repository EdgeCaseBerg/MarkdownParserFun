package space.peetseater.tokenizer;

import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.ConcreteToken;
import space.peetseater.tokenizer.tokens.NullToken;

public class TextScanner implements TokenScanner {

    protected SimpleScanner simpleScanner;

    public TextScanner() {
        this.simpleScanner = new SimpleScanner();
    }

    @Override
    public AbstractToken fromString(String input) {
        if (input.isEmpty()) {
            return NullToken.INSTANCE;
        }

        /* While a token isn't parseable by a simpleScanner, consume the input */
        char[] characters = input.toCharArray();
        StringBuilder sb = new StringBuilder(characters.length);
        AbstractToken token = NullToken.INSTANCE;
        for (int i = 0; i < characters.length && token.isNull(); i++) {
            token = simpleScanner.fromString(String.valueOf(characters[i]));
            if (token.isNull()) {
                sb.append(characters[i]);
            }
        }

        // If nothing was found, aka a simple scanner matched on the first input, then return null for text scanner
        if (sb.isEmpty()) {
            return NullToken.INSTANCE;
        }

        return new ConcreteToken("TEXT", sb.toString());
    }
}