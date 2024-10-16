package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

public class InlineCodeNode extends MarkdownNode {
    public static final String TYPE = "INLINE_CODE";

    public InlineCodeNode(String value, int consume) {
        super(TYPE, value, consume);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }

}
