package space.peetseater.tokenizer.tokens;

public class DashToken extends ConcreteToken {
    private static final String VALUE = "-";
    public static final String TYPE = "DASH";
    public static final DashToken INSTANCE = new DashToken();

    protected DashToken() {
        super(TYPE, VALUE);
    }
}
