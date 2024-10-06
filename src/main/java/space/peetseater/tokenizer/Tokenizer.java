package space.peetseater.tokenizer;

import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.EndOfFileToken;

import java.util.LinkedList;
import java.util.List;

public class Tokenizer {
    List<TokenScanner> tokenScanners = List.of(
      new SimpleScanner(),
      new TextScanner()
    );

    public TokenList tokenize(String markdown) {
        List<AbstractToken> tokens = tokensAsList(markdown);
        return new TokenList(tokens);
    }

    protected List<AbstractToken> tokensAsList(String markdown) {
        // Do NOT markdown.trim! we need the newlines!
        if (markdown == null || markdown.isEmpty()) {
            return List.of(EndOfFileToken.INSTANCE);
        }

        AbstractToken token = scanOneToken(markdown);
        String remaining = markdown.substring(token.length());
        List<AbstractToken> tokens = new LinkedList<AbstractToken>();
        tokens.add(token);
        tokens.addAll(tokensAsList(remaining));
        return tokens;
    }

    protected AbstractToken scanOneToken(String markdown) {
        for (TokenScanner scanner : tokenScanners) {
            AbstractToken token = scanner.fromString(markdown);
            if (token.isPresent()) {
                return token;
            }
        }
        throw new IllegalArgumentException("No scanner matched provided markdown: %s.\nAttempted Scanners: %s".formatted(
           markdown,
           tokenScanners.stream().map(tokenScanner -> tokenScanner.getClass().getSimpleName()).reduce("", "%s, %s"::formatted)
        ));
    }
}
