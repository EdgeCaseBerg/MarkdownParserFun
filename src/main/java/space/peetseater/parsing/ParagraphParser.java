package space.peetseater.parsing;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.parsing.ast.ParagraphNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.EndOfFileToken;
import space.peetseater.tokenizer.tokens.NewLineToken;

public class ParagraphParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        MatchedAndConsumed matchedAndConsumed = matchAtLeastOne(tokenList, new SentenceParser());
        if (matchedAndConsumed.getMatched().isEmpty()) {
            return NullNode.INSTANCE;
        }
        TokenList afterSentences = tokenList.offset(matchedAndConsumed.getConsumed());
        // Two newlines signifies a new paragraph
        // Handle end of file here for now as well
        if (
            afterSentences.typesAheadAre(NewLineToken.TYPE, NewLineToken.TYPE) ||
            afterSentences.typesAheadAre(NewLineToken.TYPE, EndOfFileToken.TYPE)
        ) {
            int consumed = matchedAndConsumed.getConsumed() + 2;
            return new ParagraphNode(matchedAndConsumed.getMatched(), consumed);
        }

        if (afterSentences.typesAheadAre(EndOfFileToken.TYPE)) {
            int consumed = matchedAndConsumed.getConsumed() + 1;
            return new ParagraphNode(matchedAndConsumed.getMatched(), consumed);
        }

        // TODO: Decide if we'll keep newline collapsing between paragraphs.
        // Or if we'll implement a newline parser that will insert <br/>
//        TokenList afterNewLines = afterSentences.offset(consumed);
//        while (afterNewLines.typesAheadAre(NewLineToken.TYPE)) {
//            consumed++;
//            afterNewLines = afterNewLines.offset(1);
//        }
        return NullNode.INSTANCE;



    }
}
