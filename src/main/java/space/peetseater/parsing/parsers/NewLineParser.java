package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.ParagraphNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.NewLineToken;

import java.util.List;

public class NewLineParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        if (!tokenList.typesAheadAre(NewLineToken.TYPE)) {
            return NullNode.INSTANCE;
        }

        int consumed = 0;
        TokenList context = tokenList;
        while (context.typesAheadAre(NewLineToken.TYPE)) {
            consumed++;
            context = context.offset(1);
        }
        return new ParagraphNode(List.of(), consumed);
    }
}
