package space.peetseater.tokenizer.tokens;

public class ParenStartToken extends ConcreteToken {

    public static final String TYPE = "PAREN_START";
    public static final String VALUE = "(";
    public static final ParenStartToken INSTANCE = new ParenStartToken();

    protected ParenStartToken() {
        super(TYPE, VALUE);
    }

}
