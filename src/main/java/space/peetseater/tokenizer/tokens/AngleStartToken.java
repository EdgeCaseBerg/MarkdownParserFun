package space.peetseater.tokenizer.tokens;

public class AngleStartToken extends ConcreteToken {

    public static final String TYPE = "ANGLE_START";
    public static final String VALUE = "<";
    public static final AngleStartToken INSTANCE = new AngleStartToken();

    protected AngleStartToken() {
        super(TYPE, VALUE);
    }
}