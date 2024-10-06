package space.peetseater.tokenizer.tokens;

public class NullToken extends AbstractToken {

    public static final NullToken INSTANCE = new NullToken();
    public static final String TYPE = "NULL";

    protected NullToken() {
        super(TYPE, "");
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public boolean isPresent() {
        return false;
    }
}
