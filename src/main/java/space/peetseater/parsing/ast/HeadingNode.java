package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

import java.util.List;

public class HeadingNode extends AbstractMarkdownNode {
    public static final String TYPE = "HEADING";
    private final int level;
    private final List<AbstractMarkdownNode> children;

    public HeadingNode(List<AbstractMarkdownNode> children, int level, int consumed) {
        super(TYPE, consumed);
        this.level = level;
        this.children = children;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }

    public int getLevel() {
        return level;
    }

    public List<AbstractMarkdownNode> getChildren() {
        return this.children;
    }
}
