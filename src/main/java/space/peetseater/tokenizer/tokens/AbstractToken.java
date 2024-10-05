package space.peetseater.tokenizer.tokens;

abstract public class AbstractToken {
    protected String type;
    protected String value;

    public AbstractToken(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public int length() {
        return value == null ? 0 : value.length();
    }

    abstract public boolean isNull();

    abstract public boolean isPresent();

    @Override
    public String toString() {
        return "type:<%s>, value:<%s>".formatted(type, value);
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
