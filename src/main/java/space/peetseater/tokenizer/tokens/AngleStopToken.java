package space.peetseater.tokenizer.tokens;

public class AngleStopToken extends ConcreteToken {

    public static final String TYPE = "ANGLE_STOP";
    public static final String VALUE = ">";
    public static final AngleStopToken INSTANCE = new AngleStopToken();

    protected AngleStopToken() {
        super(TYPE, VALUE);
    }
}