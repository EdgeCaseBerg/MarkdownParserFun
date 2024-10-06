package space.peetseater.generators;

import space.peetseater.parsing.ast.AbstractMarkdownNode;

public interface Generator<T> {
    T generate(AbstractMarkdownNode rootNode);
}
