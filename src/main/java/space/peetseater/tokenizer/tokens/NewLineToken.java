package space.peetseater.tokenizer.tokens;

public class NewLineToken extends ConcreteToken {
    public static final String TYPE = "NEWLINE";

    public NewLineToken() {
        super(TYPE, "\n");
    }
}
