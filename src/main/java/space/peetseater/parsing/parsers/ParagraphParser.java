package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.ParagraphNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.EndOfFileToken;
import space.peetseater.tokenizer.tokens.NewLineToken;

import java.util.LinkedList;
import java.util.List;

public class ParagraphParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        return matchFirst(
                tokenList,
                new SentencesFollowedByTwoNewLines(),
                new SentencesThenEndOfFileAfterNewLine(),
                new SentencesInterleavedWithNewLines()
        );
    }

    static class SentencesInterleavedWithNewLines extends TokenParser {
        @Override
        public AbstractMarkdownNode match(TokenList tokenList) {
            List<AbstractMarkdownNode> matched = new LinkedList<>();
            int consumed = 0;
            boolean consumeMore = true;
            SentenceParser sentenceParser = new SentenceParser();
            NewLineParser newLineParser = new NewLineParser();
            MatchedAndConsumed matchedAndConsumed;
            TokenList tokens = tokenList;
            do {
                matchedAndConsumed = matchAtLeastOne(tokens, sentenceParser);
                boolean hasSentence = !matchedAndConsumed.getMatched().isEmpty();
                AbstractMarkdownNode newLineMatched = newLineParser.match(tokens.offset(matchedAndConsumed.getConsumed()));
                boolean followedByNewline = newLineMatched.isPresent();
                consumeMore = hasSentence && followedByNewline;
                if (consumeMore) {
                    matched.addAll(matchedAndConsumed.getMatched());
                    int localOffset = matchedAndConsumed.getConsumed() + newLineMatched.getConsumed();
                    consumed += localOffset;
                    tokens = tokens.offset(localOffset);
                }
            } while (consumeMore);
            if (consumed > 0) {
                return new ParagraphNode(matched, consumed);
            }
            return NullNode.INSTANCE;
        }
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
