package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

import java.util.List;

public class UnorderedListNode extends ListNode {
    public UnorderedListNode(List<AbstractMarkdownNode> items, int consumed) {
        super(items, consumed);
    }

    @Override
    public boolean isOrdered() {
        return false;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
