package space.peetseater.tokenizer.tokens;

public class ConcreteToken extends AbstractToken {

    public static ConcreteToken make(String type, String value) {
        return switch (type) {
            case TextToken.TYPE       -> new TextToken(value);
            case UnderscoreToken.TYPE -> new UnderscoreToken();
            case StarToken.TYPE       -> new StarToken();
            case NewLineToken.TYPE    -> new NewLineToken();
            default                   -> new ConcreteToken(type, value);
        };
    }

    public ConcreteToken(String type, String value) {
        super(type, value);
        assert(type != null);
        assert(value != null);
    }

    public int length() {
        return value.length();
    }

    public boolean isNull() {
        return false;
    }

    public boolean isPresent() {
        return true;
    }
}
