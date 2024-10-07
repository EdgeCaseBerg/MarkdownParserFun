package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.ListItemNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.DashToken;
import space.peetseater.tokenizer.tokens.EndOfFileToken;
import space.peetseater.tokenizer.tokens.NewLineToken;
import space.peetseater.tokenizer.tokens.StarToken;

public class ListItemParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        boolean firstTokenIsDash = tokenList.typesAheadAre(DashToken.TYPE);
        boolean firstTokenIsStar = tokenList.typesAheadAre(StarToken.TYPE);

        if (!(firstTokenIsDash ^ firstTokenIsStar)) {
            return NullNode.INSTANCE;
        }
        int consumed = 1;
        MatchedAndConsumed potentialListItems = matchZeroOrMore(tokenList.offset(consumed), new SentenceParser());
        if (potentialListItems.getMatched().isEmpty()) {
            return NullNode.INSTANCE;
        }
        consumed += potentialListItems.getConsumed();

        boolean eofOrNewline = tokenList.offset(consumed).typesAheadAre(EndOfFileToken.TYPE) || tokenList.offset(consumed).typesAheadAre(NewLineToken.TYPE);
        if (!eofOrNewline) {
            return NullNode.INSTANCE;
        }

        consumed += 1;
        return new ListItemNode(potentialListItems.getMatched(), consumed, firstTokenIsDash);
    }
}
