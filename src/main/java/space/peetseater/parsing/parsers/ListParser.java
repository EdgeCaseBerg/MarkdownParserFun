package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.*;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.NewLineToken;

public class ListParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        MatchedAndConsumed itemsMatched = matchAtLeastOne(tokenList, new ListItemParser());
        if (itemsMatched.getMatched().isEmpty()) {
            return NullNode.INSTANCE;
        }

        int consumed = itemsMatched.getConsumed();
        if (tokenList.offset(itemsMatched.getConsumed()).typesAheadAre(NewLineToken.TYPE)) {
            consumed++;
        }

        return new UnorderedListNode(itemsMatched.getMatched(), consumed);
    }
}
