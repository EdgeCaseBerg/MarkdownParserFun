package space.peetseater.parsing.ast;

import java.util.List;

public abstract class ListNode extends AbstractMarkdownNode {

    public static final String TYPE = "LIST";
    private final List<AbstractMarkdownNode> items;

    public ListNode(List<AbstractMarkdownNode> items, int consumed) {
        super(TYPE, consumed);
        this.items = items;
    }
    public List<AbstractMarkdownNode> getItems() {
        return items;
    }

    abstract public boolean isOrdered();
}
