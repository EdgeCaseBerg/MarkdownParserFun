package space.peetseater.parsing.ast;

import java.util.List;

public class ParagraphNode extends AbstractMarkdownNode {
    public static final String TYPE = "PARAGRAPH";
    private final List<AbstractMarkdownNode> sentences;

    public ParagraphNode(List<AbstractMarkdownNode> sentences, int consumed) {
        super(TYPE, consumed);
        this.sentences = sentences;
    }

    public List<AbstractMarkdownNode> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractMarkdownNode abstractMarkdownNode : sentences) {
            stringBuilder.append("SENTENCE:<");
            stringBuilder.append(abstractMarkdownNode.toString());
            stringBuilder.append(">");
        }
        return "PARAGRAPH:<%s>".formatted(stringBuilder.toString());
    }
}
