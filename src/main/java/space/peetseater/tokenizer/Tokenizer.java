package space.peetseater.tokenizer;

import space.peetseater.tokenizer.scanners.SimpleScanner;
import space.peetseater.tokenizer.scanners.TextScanner;
import space.peetseater.tokenizer.scanners.TokenScanner;
import space.peetseater.tokenizer.tokens.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Tokenizer {
    List<TokenScanner> tokenScanners = List.of(
        new TextScanner(),
        new SimpleScanner()
    );

    public TokenList tokenize(String markdown) {
        List<AbstractToken> tokens = tokensAsList(markdown);
        List<AbstractToken> processed = collapseTokensWithoutMeaning(tokens);
        return new TokenList(processed);
    }

    private List<AbstractToken> collapseTokensWithoutMeaning(List<AbstractToken> tokens) {
        for (int i = 1; i < tokens.size(); i++) {
            AbstractToken token = tokens.get(i);
            if (token.equals(PoundToken.INSTANCE)) {
                AbstractToken previousToken = tokens.get(i - 1);
                boolean poundTokenOrNewline = previousToken.equals(PoundToken.INSTANCE) || previousToken.equals(NewLineToken.INSTANCE);
                if (!poundTokenOrNewline) {
                    tokens.set(i, new TextToken(PoundToken.VALUE));
                }
            }
        }
        return tokens;
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
