package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.ItalicsNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.TextToken;
import space.peetseater.tokenizer.tokens.UnderscoreToken;

/**
 * Parses __TEXT__ as italics Text
 * Parses _TEXT_ as italics text
 **/
public class ItalicsParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        if (tokenList.isEmpty()) {
            return NullNode.INSTANCE;
        }

        if (tokenList.typesAheadAre(UnderscoreToken.TYPE, UnderscoreToken.TYPE, TextToken.TYPE, UnderscoreToken.TYPE, UnderscoreToken.TYPE)) {
            return new ItalicsNode(tokenList.get(2).getValue(), 5);
        }

        if (tokenList.typesAheadAre(UnderscoreToken.TYPE, TextToken.TYPE, UnderscoreToken.TYPE)) {
            return new ItalicsNode(tokenList.get(1).getValue(), 3);
        }

        return NullNode.INSTANCE;
    }
}
