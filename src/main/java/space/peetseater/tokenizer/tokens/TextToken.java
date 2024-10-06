package space.peetseater.tokenizer.tokens;

public class TextToken extends ConcreteToken {
    public static final String TYPE = "TEXT";

    public TextToken(String value) {
        super(TYPE, value);
    }
}
