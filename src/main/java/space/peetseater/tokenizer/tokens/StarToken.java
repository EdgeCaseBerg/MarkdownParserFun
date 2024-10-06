package space.peetseater.tokenizer.tokens;

public class StarToken extends ConcreteToken {

    public static final String TYPE = "STAR";
    public static final String VALUE = "*";

    public StarToken() {
        super(TYPE, VALUE);
    }
}
