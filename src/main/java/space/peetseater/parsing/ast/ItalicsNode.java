package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

public class ItalicsNode extends MarkdownNode {
    public static final String TYPE = "ITALICS";

    public ItalicsNode(String value, int consume) {
        super(TYPE, value, consume);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
