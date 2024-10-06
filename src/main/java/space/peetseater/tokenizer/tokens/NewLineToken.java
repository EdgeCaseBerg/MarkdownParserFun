package space.peetseater.tokenizer.tokens;

public class NewLineToken extends ConcreteToken {
    public static final String TYPE = "NEWLINE";
    public static final String VALUE = "\n";
    public static final NewLineToken INSTANCE = new NewLineToken();

    private NewLineToken() {
        super(TYPE, VALUE);
    }
}
