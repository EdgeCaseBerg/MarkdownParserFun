package space.peetseater.generators;

import space.peetseater.parsing.ast.*;

import java.util.LinkedList;

public class Flattener extends BaseAstVisitor {

    private final LinkedList<AbstractMarkdownNode> nodes;
    private int bodyCounts;
    private int listCounts;
    private int paragraphCounts;
    private int headingCounts;

    public Flattener() {
        this.paragraphCounts = 0;
        this.listCounts = 0;
        this.bodyCounts = 0;
        this.headingCounts = 0;
        this.nodes = new LinkedList<AbstractMarkdownNode>();
    }

    public LinkedList<AbstractMarkdownNode> getNodes() {
        return nodes;
    }

    @Override
    public void visit(BodyNode node) {
        super.visit(node);
        bodyCounts += node.getBodyParts().size();
    }

    @Override
    public void visit(ParagraphNode node) {
        super.visit(node);
        paragraphCounts++;
    }

    @Override
    public void visit(UnorderedListNode listNode) {
        super.visit(listNode);
        listCounts++;
    }

    @Override
    public void visit(ListItemNode node) {
        super.visit(node);
    }

    @Override
    public void visit(NullNode node) {
        this.nodes.add(node);
    }

    @Override
    public void visit(BoldNode node) {
        this.nodes.add(node);
    }

    @Override
    public void visit(ItalicsNode node) {
        this.nodes.add(node);
    }

    @Override
    public void visit(TextNode node) {
        this.nodes.add(node);
    }

    @Override
    public void visit(InlineCodeNode inlineCodeNode) {
        this.nodes.add(inlineCodeNode);
    }

    @Override
    public void visit(HeadingNode headingNode) {
        headingCounts++;
        this.nodes.add(headingNode);
    }

    public int getBodyCounts() {
        return bodyCounts;
    }

    public int getListCounts() {
        return listCounts;
    }

    public int getParagraphCounts() {
        return paragraphCounts;
    }

    public int getHeadingCounts() {
        return headingCounts;
    }
}
