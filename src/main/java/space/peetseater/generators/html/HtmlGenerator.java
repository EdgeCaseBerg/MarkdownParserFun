package space.peetseater.generators.html;

import space.peetseater.generators.Generator;
import space.peetseater.parsing.ast.AbstractMarkdownNode;

public class HtmlGenerator implements Generator<String> {
    @Override
    public String generate(AbstractMarkdownNode rootNode) {
        HtmlStringVisitor htmlVisitor = new HtmlStringVisitor();
        rootNode.accept(htmlVisitor);
        return htmlVisitor.getHTMLString();
    }
}
