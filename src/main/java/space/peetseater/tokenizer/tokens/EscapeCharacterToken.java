package space.peetseater.tokenizer.tokens;

public class EscapeCharacterToken extends ConcreteToken {

    public static final String TYPE = "ESCAPE";
    public static final String VALUE = "\\";
    public static final EscapeCharacterToken INSTANCE = new EscapeCharacterToken();

    protected EscapeCharacterToken() {
        super(TYPE, VALUE);
    }
}