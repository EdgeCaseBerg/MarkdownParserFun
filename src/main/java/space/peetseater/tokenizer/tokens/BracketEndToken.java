package space.peetseater.tokenizer.tokens;

public class BracketEndToken extends ConcreteToken {

    public static final String TYPE = "BRACKET_END";
    public static final String VALUE = "]";
    public static final BracketEndToken INSTANCE = new BracketEndToken();

    protected BracketEndToken() {
        super(TYPE, VALUE);
    }
}
