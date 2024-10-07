package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

import java.util.List;

// TODO: should we push down ordered versus unordered list item?
// It would make the visitor cleaner.
public class ListItemNode extends AbstractMarkdownNode {
    public static final String TYPE = "LIST_ITEM";
    private final List<AbstractMarkdownNode> runs;
    private final boolean isOrdered;

    public ListItemNode(List<AbstractMarkdownNode> run, int consumed, boolean isOrdered) {
        super(TYPE, consumed);
        this.runs = run;
        this.isOrdered = isOrdered;
    }

    @Override
    public void accept(AstVisitor visitor) {
          visitor.visit(this);
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public List<AbstractMarkdownNode> getRuns() {
        return runs;
    }
}
