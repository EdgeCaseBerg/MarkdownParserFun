package space.peetseater.tokenizer.tokens;

public class ConcreteToken extends AbstractToken {

    public static ConcreteToken make(String type, String value) {
        return switch (type) {
            case TextToken.TYPE       -> new TextToken(value);
            case UnderscoreToken.TYPE -> UnderscoreToken.INSTANCE;
            case StarToken.TYPE       -> StarToken.INSTANCE;
            case NewLineToken.TYPE    -> NewLineToken.INSTANCE;
            default                   -> new ConcreteToken(type, value);
        };
    }


    protected ConcreteToken(String type, String value) {
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
