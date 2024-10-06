package space.peetseater.parsing;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.TextNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.TextToken;

public class TextParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        if (!tokenList.typesAheadAre(TextToken.TYPE)) {
            return NullNode.INSTANCE;
        }

        return new TextNode(tokenList.get(0).getValue(), 1);
    }
}
