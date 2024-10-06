package space.peetseater.parsing.parsers;


import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;

import java.util.LinkedList;
import java.util.List;

public abstract class TokenParser {
    abstract public AbstractMarkdownNode match(TokenList tokenList);

    /** Tries to match one parser, in order, and return the matching node. If none match, NullNode is returned
     */
    public AbstractMarkdownNode matchFirst(TokenList tokens, TokenParser ...parsers) {
        for (TokenParser tokenParser : parsers) {
            AbstractMarkdownNode node = tokenParser.match(tokens);
            if (node.isPresent()) {
                return node;
            }
        }
        return NullNode.INSTANCE;
    }

    public MatchedAndConsumed matchAtLeastOne(TokenList tokens, TokenParser parser) {
        MatchedAndConsumed matchAndConsumed = matchZeroOrMore(tokens, parser);
        if (matchAndConsumed.getMatched().isEmpty()) {
            return new MatchedAndConsumed(List.of(), 0);
        }
        return matchAndConsumed;
    }

    /** Tries to match as many times as possible, returning all matched nodes. Kleene star */
    public MatchedAndConsumed matchZeroOrMore(TokenList tokens, TokenParser tokenParser) {
        int consumed = 0;
        List<AbstractMarkdownNode> matched = new LinkedList<>();
        AbstractMarkdownNode node;
        while(true) {
            TokenList unConsumedTokens = tokens.offset(consumed);
            node = tokenParser.match(unConsumedTokens);
            if (node.isNull()) {
                break;
            }
            matched.add(node);
            consumed += node.getConsumed();
        }
        return new MatchedAndConsumed(matched, consumed);
    }

}
