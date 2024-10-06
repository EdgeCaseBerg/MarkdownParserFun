package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

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

    @Override
    public String toString() {
        return getType();
    }

    abstract public void accept(AstVisitor visitor);
}
