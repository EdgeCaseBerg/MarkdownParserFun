package space.peetseater.parsing.ast;

public class ItalicsNode extends MarkdownNode {
    public static final String TYPE = "ITALICS";

    public ItalicsNode(String value, int consume) {
        super(TYPE, value, consume);
    }
}
