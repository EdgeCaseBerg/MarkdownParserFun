package space.peetseater.parsing.ast;

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
}
