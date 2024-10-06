package space.peetseater.tokenizer.tokens;

public class UnderscoreToken extends ConcreteToken{

    public static final String TYPE = "UNDERSCORE";
    public static final UnderscoreToken INSTANCE = new UnderscoreToken();
    private static final String VALUE = "_";

    private UnderscoreToken() {
        super(TYPE, VALUE);
    }
}
