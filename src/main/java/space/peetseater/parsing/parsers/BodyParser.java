package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.BodyNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;

import java.util.LinkedList;
import java.util.List;

public class BodyParser extends TokenParser {
    List<TokenParser> parsersToApply;

    public BodyParser() {
         parsersToApply = new LinkedList<TokenParser>();
         parsersToApply.add(new HeadingParser());
         parsersToApply.add(new ParagraphParser());
         parsersToApply.add(new ListParser());
         parsersToApply.add(new NewLineParser());
    }

    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        // Consume initial whitespace
        MatchedAndConsumed m = matchZeroOrMore(tokenList, new NewLineParser());
        TokenList trimmed = tokenList.offset(m.getConsumed());

        List<AbstractMarkdownNode> bodyParts = new LinkedList<>();
        boolean consumeMore = true;
        while (consumeMore) {
            // Match first
            AbstractMarkdownNode node = matchFirst(trimmed, parsersToApply.toArray(TokenParser[]::new));
            if (node.isPresent()) {
                bodyParts.add(node);
                trimmed = trimmed.offset(node.getConsumed());
            }
            consumeMore = node.isPresent();
        }

        if (bodyParts.isEmpty()) {
            return NullNode.INSTANCE;
        }
        int bodyPartConsumption = bodyParts.stream().mapToInt(AbstractMarkdownNode::getConsumed).sum();
        return new BodyNode(bodyParts, bodyPartConsumption + m.getConsumed());
    }
}
