package space.peetseater.tokenizer.tokens;

import java.util.Arrays;

abstract public class AbstractToken {
    protected String type;
    protected String value;

    public AbstractToken(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public boolean isOneOfType(String ...types) {
        return Arrays.asList(types).contains(type);
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
