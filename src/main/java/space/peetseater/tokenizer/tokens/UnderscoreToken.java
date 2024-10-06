package space.peetseater.tokenizer.tokens;

public class UnderscoreToken extends ConcreteToken{

    public static final String TYPE = "UNDERSCORE";
    private static final String VALUE = "_";

    public UnderscoreToken() {
        super(TYPE, VALUE);
    }
}
