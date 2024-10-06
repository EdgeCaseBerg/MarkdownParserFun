package space.peetseater.parsing.ast;

public class TextNode extends MarkdownNode {

    public static final String TYPE = "TEXT";

    public TextNode(String value, int consumed) {
        super(TYPE, value, consumed);
    }
}
