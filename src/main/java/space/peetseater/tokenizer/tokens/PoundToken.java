package space.peetseater.tokenizer.tokens;

public class PoundToken extends ConcreteToken {

    public static final String TYPE = "POUND";
    public static final String VALUE = "#";
    public static final PoundToken INSTANCE = new PoundToken();

    protected PoundToken() {
        super(TYPE, VALUE);
    }
}
