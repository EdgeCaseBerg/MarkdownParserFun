package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

public class HeadingNode extends MarkdownNode {
    public static final String TYPE = "HEADING";
    private final int level;

    public HeadingNode(String value, int level, int consumed) {
        super(TYPE, value, consumed);
        this.level = level;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }

    public int getLevel() {
        return level;
    }
}
