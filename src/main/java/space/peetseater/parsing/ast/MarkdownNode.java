package space.peetseater.parsing.ast;

public abstract class MarkdownNode extends AbstractMarkdownNode {
    protected final String value;

    public MarkdownNode(String type, String value, int consumed) {
        super(type, consumed);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "%s:<%s>".formatted(getType(), getValue());
    }
}
