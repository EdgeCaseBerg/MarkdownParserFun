package space.peetseater.tokenizer.tokens;

public class ColonToken extends ConcreteToken {

    public static final String TYPE = "COLON";
    public static final String VALUE = ":";
    public static final ColonToken INSTANCE = new ColonToken();

    protected ColonToken() {
        super(TYPE, VALUE);
    }
}
