package space.peetseater.tokenizer.tokens;

public class EndOfFileToken extends ConcreteToken {

    public static final EndOfFileToken INSTANCE = new EndOfFileToken();
    public static final String TYPE = "EOF";

    protected EndOfFileToken() {
        super(TYPE, "");
    }
}
