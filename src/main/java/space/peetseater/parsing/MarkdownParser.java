package space.peetseater.parsing;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.parsers.BodyParser;
import space.peetseater.parsing.parsers.TokenParser;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.EndOfFileToken;

public class MarkdownParser extends TokenParser {
    public AbstractMarkdownNode parse(TokenList tokens) {
        BodyParser bp = new BodyParser();

        AbstractMarkdownNode body = bp.match(tokens);
        int consumed = body.getConsumed();
        TokenList remainingTokens = tokens.offset(consumed);
        if (remainingTokens.typesAheadAre(EndOfFileToken.TYPE)) {
            consumed++;
        }
        if (consumed != tokens.size()) {
            throw new IllegalArgumentException(
                "Syntax Error, consumed tokens did not match parsed tokens! Length mismatch %s vs %s\n".formatted(consumed, tokens.size()) +
                "unconsumed tokens: %s".formatted(tokens.offset(consumed))
            );
        }
        return body;
    }

    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        return parse(tokenList);
    }
}
