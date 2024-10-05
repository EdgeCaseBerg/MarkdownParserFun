package space.peetseater.tokenizer;

public class NullToken extends AbstractToken {

    public static final NullToken INSTANCE = new NullToken();

    protected NullToken() {
        super("NULL", "");
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
