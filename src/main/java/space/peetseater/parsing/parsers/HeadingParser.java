package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.HeadingNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.PoundToken;

/**
 * A heading must start at the beginning of the line with #, and can be up to 6 #'s before the rest of the content
 * is consumed as a regular sentence which may include bold, or other styling of text.
 * */
public class HeadingParser extends TokenParser{
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        if (!tokenList.typesAheadAre(PoundToken.TYPE)) {
            return NullNode.INSTANCE;
        }
        int headingLevel = 0;
        do {
            headingLevel++;
        } while (tokenList.offset(headingLevel).typesAheadAre(PoundToken.TYPE));

        SentenceParser sentenceParser = new SentenceParser();
        NewLineParser newlineParser = new NewLineParser();
        TokenList tokensAfterHeaderSymbols = tokenList.offset(headingLevel);
        MatchedAndConsumed matched = matchAtLeastOne(tokensAfterHeaderSymbols, sentenceParser);
        MatchedAndConsumed endOfLineAndTrailing = newlineParser.matchZeroOrMore(tokensAfterHeaderSymbols.offset(matched.getConsumed()), newlineParser);

        return new HeadingNode(matched.getMatched(), headingLevel, headingLevel + matched.getConsumed() + endOfLineAndTrailing.getConsumed());
    }
}
