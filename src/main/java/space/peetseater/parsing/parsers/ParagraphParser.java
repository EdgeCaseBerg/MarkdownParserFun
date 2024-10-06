package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.ParagraphNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.EndOfFileToken;
import space.peetseater.tokenizer.tokens.NewLineToken;

public class ParagraphParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        return matchFirst(
                tokenList,
                new SentencesFollowedByTwoNewLines(),
                new SentencesThenEndOfFileAfterNewLine()
        );
    }

    static class SentencesThenEndOfFileAfterNewLine extends TokenParser {
        @Override
        public AbstractMarkdownNode match(TokenList tokenList) {
            MatchedAndConsumed matchedAndConsumed = matchZeroOrMore(tokenList, new SentenceParser());
            if (matchedAndConsumed.getMatched().isEmpty()) {
                return NullNode.INSTANCE;
            }

            TokenList afterSentences = tokenList.offset(matchedAndConsumed.getConsumed());
            if (afterSentences.typesAheadAre(EndOfFileToken.TYPE)) {
                int consumed = matchedAndConsumed.getConsumed() + 1;
                return new ParagraphNode(matchedAndConsumed.getMatched(), consumed);
            }

            if (afterSentences.typesAheadAre(NewLineToken.TYPE, EndOfFileToken.TYPE)) {
                int consumed = matchedAndConsumed.getConsumed() + 2;
                return new ParagraphNode(matchedAndConsumed.getMatched(), consumed);
            }

            return NullNode.INSTANCE;
        }

    }
    static class SentencesFollowedByTwoNewLines extends TokenParser {
        @Override
        public AbstractMarkdownNode match(TokenList tokenList) {
            MatchedAndConsumed matchedAndConsumed = matchAtLeastOne(tokenList, new SentenceParser());
            if (matchedAndConsumed.getMatched().isEmpty()) {
                return NullNode.INSTANCE;
            }
            TokenList afterSentences = tokenList.offset(matchedAndConsumed.getConsumed());
            if (afterSentences.typesAheadAre(NewLineToken.TYPE, NewLineToken.TYPE)) {
                int consumed = matchedAndConsumed.getConsumed() + 2;
                return new ParagraphNode(matchedAndConsumed.getMatched(), consumed);
            }

            return NullNode.INSTANCE;
        }


    }
}
