package space.peetseater.tokenizer.tokens;

public class StarToken extends ConcreteToken {

    public static final String TYPE = "STAR";
    public static final String VALUE = "*";
    public static final StarToken INSTANCE = new StarToken();

    private StarToken() {
        super(TYPE, VALUE);
    }
}
