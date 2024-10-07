package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.*;
import space.peetseater.tokenizer.TokenList;

public class ListParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        // TODO: Do we need a StartOfFile token to handle a list at the very start?
        // Then we could match on a newline followed by line items as well
        MatchedAndConsumed itemsMatched = matchAtLeastOne(tokenList, new ListItemParser());
        if (itemsMatched.getMatched().isEmpty()) {
            return NullNode.INSTANCE;
        }
        return new UnorderedListNode(itemsMatched.getMatched(), itemsMatched.getConsumed());
    }
}
