package space.peetseater.tokenizer.scanners;

import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.EscapeCharacterToken;
import space.peetseater.tokenizer.tokens.NullToken;
import space.peetseater.tokenizer.tokens.TextToken;

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
        boolean isEscaped = false;
        for (int i = 0; i < characters.length; i++) {
            token = simpleScanner.fromString(String.valueOf(characters[i]));
            switch (token.getType()) {
                case NullToken.TYPE -> sb.append(characters[i]);
                case EscapeCharacterToken.TYPE -> {
                    if (isEscaped) { // if a \ was before this one, then escape the escape
                        sb.append(characters[i]);
                        isEscaped = false;
                    } else {
                        isEscaped = true;
                    }
                }
                default -> {
                    // If we are not escaping but have found a meaningful token, then cease
                    // processing the string we've been building and carry on so the next
                    // scanner could handle it
                    if (!isEscaped) {
                        if (sb.isEmpty()) {
                            return NullToken.INSTANCE;
                        }

                        return new TextToken(sb.toString());
                    }
                    // We are escaping a literal
                    sb.append(characters[i]);
                }
            }
        }


        // If nothing was found, aka a simple scanner matched on the first input, then return null for text scanner
        if (sb.isEmpty()) {
            return NullToken.INSTANCE;
        }

        return new TextToken(sb.toString());
    }
}
