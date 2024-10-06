package space.peetseater.parsing.ast;

import java.util.List;

public class BodyNode extends AbstractMarkdownNode {
    public static final String TYPE = "BODY";
    private final List<AbstractMarkdownNode> bodyParts;

    public BodyNode(List<AbstractMarkdownNode> paragraphs, int consumed) {
        super(TYPE, consumed);
        this.bodyParts = paragraphs;
    }

    public List<AbstractMarkdownNode> getBodyParts() {
        return bodyParts;
    }
}
