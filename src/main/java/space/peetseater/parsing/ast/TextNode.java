package space.peetseater.parsing.ast;

import space.peetseater.generators.AstVisitor;

import java.util.Objects;

public class TextNode extends MarkdownNode {

    public static final String TYPE = "TEXT";

    public TextNode(String value, int consumed) {
        super(TYPE, value, consumed);
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TextNode tt ) {
            return value.equals(tt.value) && consumed == tt.consumed;
        }
        return Objects.equals(value, obj);
    }
}
