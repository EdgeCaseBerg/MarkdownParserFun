package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.BodyNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;

public class BodyParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        // Consume initial whitesppace
        MatchedAndConsumed m = matchZeroOrMore(tokenList, new NewLineParser());
        TokenList prefixTrim = tokenList.offset(m.getConsumed());

        MatchedAndConsumed paragraphs = matchZeroOrMore(prefixTrim, new ParagraphParser());
        if (paragraphs.getMatched().isEmpty()) {
            return NullNode.INSTANCE;
        }

        return new BodyNode(paragraphs.getMatched(), paragraphs.getConsumed() + m.getConsumed());
    }
}
