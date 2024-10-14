package space.peetseater.tokenizer.tokens;

public class EqualsToken extends ConcreteToken {

    public static final String TYPE = "EQUALS";
    public static final String VALUE = "=";
    public static final EqualsToken INSTANCE = new EqualsToken();

    protected EqualsToken() {
        super(TYPE, VALUE);
    }
}
