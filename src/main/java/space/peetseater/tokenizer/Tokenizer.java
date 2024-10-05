package space.peetseater.tokenizer;

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
        if (markdown == null || markdown.trim().isEmpty()) {
            return List.of(new EndOfFileToken());
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
