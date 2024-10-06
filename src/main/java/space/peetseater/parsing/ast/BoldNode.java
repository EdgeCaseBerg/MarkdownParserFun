package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

public class BoldNode extends MarkdownNode {

    public static final String TYPE = "BOLD";

    public BoldNode(String value, int consumed) {
        super(TYPE, value, consumed);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
