package space.peetseater.parsing.ast;

abstract public class AbstractMarkdownNode {
    protected final String type;
    protected final int consumed;

    public AbstractMarkdownNode(String type, int consumed) {
        this.type = type;
        this.consumed = consumed;
    }

    public String getType() {
        return type;
    }

    public int getConsumed() {
        return consumed;
    }

    public boolean isPresent() {
        return true;
    }

    public boolean isNull() {
        return false;
    }
}
