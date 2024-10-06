package space.peetseater.parsing.ast;

import java.util.List;

public class BodyNode extends AbstractMarkdownNode {
    public static final String TYPE = "BODY";
    private final List<ParagraphNode> paragraphs;

    public BodyNode(List<ParagraphNode> paragraphs, int consumed) {
        super(TYPE, consumed);
        this.paragraphs = paragraphs;
    }

    public List<ParagraphNode> getParagraphs() {
        return paragraphs;
    }
}
