package space.peetseater.tokenizer.tokens;

public class BracketStartToken extends ConcreteToken {

    public static final String TYPE = "BRACKET_START";
    public static final String VALUE = "[";
    public static final BracketStartToken INSTANCE = new BracketStartToken();

    protected BracketStartToken() {
        super(TYPE, VALUE);
    }
}
