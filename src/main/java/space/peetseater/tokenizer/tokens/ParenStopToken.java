package space.peetseater.tokenizer.tokens;

public class ParenStopToken extends ConcreteToken {

    public static final String TYPE = "PAREN_STOP";
    public static final String VALUE = ")";
    public static final ParenStopToken INSTANCE = new ParenStopToken();

    protected ParenStopToken() {
        super(TYPE, VALUE);
    }
}
