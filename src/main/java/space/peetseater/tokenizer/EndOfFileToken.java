package space.peetseater.tokenizer;

public class EndOfFileToken extends ConcreteToken {

    public static final EndOfFileToken INSTANCE = new EndOfFileToken();

    protected EndOfFileToken() {
        super("EOF", "");
    }
}
