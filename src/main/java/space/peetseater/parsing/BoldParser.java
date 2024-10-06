package space.peetseater.parsing;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.BoldNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.StarToken;
import space.peetseater.tokenizer.tokens.TextToken;

/**
 * Parses **TEXT** as Bold Text
 * Parses *TEXT* as Bold text
 **/
public class BoldParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        if (tokenList.isEmpty()) {
            return NullNode.INSTANCE;
        }

        if (tokenList.typesAheadAre(StarToken.TYPE, StarToken.TYPE, TextToken.TYPE, StarToken.TYPE, StarToken.TYPE)) {
            return new BoldNode(tokenList.get(2).getValue(), 5);
        }

        if (tokenList.typesAheadAre(StarToken.TYPE, TextToken.TYPE, StarToken.TYPE)) {
            return new BoldNode(tokenList.get(1).getValue(), 3);
        }

        return NullNode.INSTANCE;
    }
}
