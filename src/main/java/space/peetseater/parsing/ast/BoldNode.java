package space.peetseater.parsing.ast;

public class BoldNode extends MarkdownNode {

    public static final String TYPE = "BOLD";

    public BoldNode(String value, int consumed) {
        super(TYPE, value, consumed);
    }
}
