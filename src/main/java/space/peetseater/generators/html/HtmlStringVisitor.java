package space.peetseater.generators.html;

import space.peetseater.generators.NoOpAstVisitor;
import space.peetseater.parsing.ast.*;

public class HtmlStringVisitor extends NoOpAstVisitor {

    String indentString;
    int indentLevel;
    StringBuilder stringBuilder;

    public HtmlStringVisitor() {
        stringBuilder = new StringBuilder();
        indentLevel = 0;
        indentString = "  ";
    }

    @Override
    public void visit(BoldNode node) {
        createTagWithTextFromNodeValue(node, "strong");
    }

    protected void createTagWithTextFromNodeValue(MarkdownNode node, String tag) {
        stringBuilder.append("<%s>".formatted(tag));
        stringBuilder.append(node.getValue());
        stringBuilder.append("</%s>".formatted(tag));
    }

    private void indent() {
        stringBuilder.append(getIndentString().repeat(indentLevel));
    }

    @Override
    public void visit(ItalicsNode node) {
        createTagWithTextFromNodeValue(node, "em");
    }

    @Override
    public void visit(TextNode node) {
        stringBuilder.append(node.getValue());
    }

    @Override
    public void visit(BodyNode node) {
        for (AbstractMarkdownNode bodyPart : node.getBodyParts()) {
            indent();
            bodyPart.accept(this);
        }
    }

    @Override
    public void visit(ParagraphNode node) {
        stringBuilder.append("<p>\n");
        indentLevel++;
        indent();
        for (AbstractMarkdownNode child : node.getSentences()) {
            child.accept(this);
        }
        indentLevel--;
        stringBuilder.append("\n</p>");
        stringBuilder.append("\n");
    }

    private String getIndentString() {
        return indentString;
    }

    public String getHTMLString() {
        return stringBuilder.toString();
    }
}
