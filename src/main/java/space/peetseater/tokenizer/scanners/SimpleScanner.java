package space.peetseater.tokenizer.scanners;

import space.peetseater.tokenizer.tokens.*;

import java.util.HashMap;

public class SimpleScanner implements TokenScanner {

    public HashMap<Character, String> tokenToType;

    public SimpleScanner() {
        tokenToType = new HashMap<>();
        tokenToType.put('_',  UnderscoreToken.TYPE);
        tokenToType.put('*',  StarToken.TYPE);
        tokenToType.put('\n', NewLineToken.TYPE);
        tokenToType.put('-',  DashToken.TYPE);
        tokenToType.put('[',  BracketStartToken.TYPE);
        tokenToType.put(']',  BracketEndToken.TYPE);
        tokenToType.put('(',  ParenStartToken.TYPE);
        tokenToType.put(')',  ParenStopToken.TYPE);
        tokenToType.put('#',  PoundToken.TYPE);
        tokenToType.put('=',  EqualsToken.TYPE);
        tokenToType.put('`',  BacktickToken.TYPE);
        tokenToType.put('<',  AngleStartToken.TYPE);
        tokenToType.put('>',  AngleStopToken.TYPE);
        tokenToType.put('\\', EscapeCharacterToken.TYPE);
        tokenToType.put(':',  ColonToken.TYPE);
    }

    @Override
    public AbstractToken fromString(String input) {
        if (input.isEmpty()) {
            return NullToken.INSTANCE;
        }

        char character = input.charAt(0);
        if (tokenToType.containsKey(character)) {
            String tokenType = tokenToType.get(character);
            return ConcreteToken.make(tokenType, String.valueOf(character));
        }

        return NullToken.INSTANCE;
    }
}
