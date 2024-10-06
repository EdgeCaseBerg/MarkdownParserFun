package space.peetseater.parsing;

import space.peetseater.parsing.ast.AbstractMarkdownNode;

import java.util.List;

public class MatchedAndConsumed {
    private final List<AbstractMarkdownNode> matched;
    private final int consumed;

    MatchedAndConsumed(List<AbstractMarkdownNode> matched, int consumed) {
        this.matched = matched;
        this.consumed = consumed;
    }

    public List<AbstractMarkdownNode> getMatched() {
        return matched;
    }

    public int getConsumed() {
        return consumed;
    }
}
