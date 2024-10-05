package space.peetseater.tokenizer;

import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.ConcreteToken;
import space.peetseater.tokenizer.tokens.NullToken;

import java.util.HashMap;

public class SimpleScanner implements TokenScanner {

    public HashMap<Character, String> tokenToType;

    public SimpleScanner() {
        tokenToType = new HashMap<>();
        tokenToType.put('_', "UNDERSCORE");
        tokenToType.put('*', "STAR");
        tokenToType.put('\n', "NEWLINE");
    }

    @Override
    public AbstractToken fromString(String input) {
        if (input.isEmpty()) {
            return NullToken.INSTANCE;
        }

        char character = input.charAt(0);
        if (tokenToType.containsKey(character)) {
            String tokenType = tokenToType.get(character);
            return new ConcreteToken(tokenType, String.valueOf(character));
        }

        return NullToken.INSTANCE;
    }
}
