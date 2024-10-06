package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

public class NullNode extends AbstractMarkdownNode {
    public static final String TYPE = "NULL";
    private static final int NULL_CONSUMED = 0;
    public static NullNode INSTANCE = new NullNode();

    protected NullNode() {
        super(TYPE, NULL_CONSUMED);
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
