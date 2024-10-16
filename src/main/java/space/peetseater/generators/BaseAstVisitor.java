package space.peetseater.generators;

import space.peetseater.parsing.ast.*;

public abstract class BaseAstVisitor implements AstVisitor {

    @Override
    public void visit(NullNode node) {}
    @Override
    public void visit(BoldNode node) {}
    @Override
    public void visit(ItalicsNode node) {}
    @Override
    public void visit(TextNode node) {}

    @Override
    public void visit(BodyNode node) {
        for (AbstractMarkdownNode child : node.getBodyParts()) {
            child.accept(this);
        }
    }
    @Override
    public void visit(ParagraphNode node) {
        for (AbstractMarkdownNode child : node.getSentences()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(UnorderedListNode listNode) {
        for (AbstractMarkdownNode item : listNode.getItems()) {
            item.accept(this);
        }
    }

    @Override
    public void visit(HeadingNode headingNode) {}

    @Override
    public void visit(InlineCodeNode inlineCodeNode) {}

    @Override
    public void visit(ListItemNode node) {
        for (AbstractMarkdownNode child : node.getRuns()) {
            child.accept(this);
        }
    }
}
