package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

public class CodeBlockNode extends MarkdownNode {

    public static final String TYPE = "CODE_BLOCK";

    public CodeBlockNode(String value, int consumed) {
        super(TYPE, value, consumed);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}
