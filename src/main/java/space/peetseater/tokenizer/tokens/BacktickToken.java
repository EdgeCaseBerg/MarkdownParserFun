package space.peetseater.tokenizer.tokens;

public class BacktickToken extends ConcreteToken {

    public static final String TYPE = "BACKTICK";
    public static final String VALUE = "`";
    public static final BacktickToken INSTANCE = new BacktickToken();

    protected BacktickToken() {
        super(TYPE, VALUE);
    }
}