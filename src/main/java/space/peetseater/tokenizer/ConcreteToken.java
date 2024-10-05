package space.peetseater.tokenizer;

public class ConcreteToken extends AbstractToken {
    public ConcreteToken(String type, String value) {
        super(type, value);
        assert(type != null);
        assert(value != null);
    }

    public int length() {
        return value.length();
    }

    public boolean isNull() {
        return false;
    }

    public boolean isPresent() {
        return true;
    }
}
