package space.peetseater.generators;

import space.peetseater.parsing.ast.*;

public abstract class NoOpAstVisitor implements AstVisitor {

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
}
