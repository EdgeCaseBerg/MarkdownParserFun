package space.peetseater.tokenizer;

import space.peetseater.tokenizer.scanners.SimpleScanner;
import space.peetseater.tokenizer.scanners.TextScanner;
import space.peetseater.tokenizer.scanners.TokenScanner;
import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.EndOfFileToken;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<AbstractToken> tokens = new LinkedList<>();
        while (!markdown.isEmpty()) {
            AbstractToken token = scanOneToken(markdown);
            tokens.add(token);
            markdown = markdown.substring(token.length());
        }

        tokens.add(EndOfFileToken.INSTANCE);
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
           tokenScanners.stream()
                   .map(tokenScanner -> tokenScanner.getClass().getSimpleName())
                   .collect(Collectors.joining(", "))
        ));
    }
}
