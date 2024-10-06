package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

public class TextNode extends MarkdownNode {

    public static final String TYPE = "TEXT";

    public TextNode(String value, int consumed) {
        super(TYPE, value, consumed);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
