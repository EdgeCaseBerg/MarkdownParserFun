package space.peetseater.generators;

import space.peetseater.parsing.ast.*;

public interface AstVisitor {
    void visit(NullNode node);

    void visit(BoldNode node);

    void visit(ItalicsNode node);

    void visit(TextNode node);

    void visit(BodyNode node);

    void visit(ParagraphNode node);

    void visit(ListItemNode node);

    void visit(UnorderedListNode listNode);
}
