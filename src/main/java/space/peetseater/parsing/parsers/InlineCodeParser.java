package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.InlineCodeNode;
import space.peetseater.parsing.ast.ItalicsNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.BacktickToken;
import space.peetseater.tokenizer.tokens.TextToken;

public class InlineCodeParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        if (tokenList.isEmpty()) {
            return NullNode.INSTANCE;
        }

        if (tokenList.typesAheadAre(BacktickToken.TYPE, TextToken.TYPE, BacktickToken.TYPE)) {
            return new InlineCodeNode(tokenList.get(1).getValue(), 3);
        }

        return NullNode.INSTANCE;
    }
}
