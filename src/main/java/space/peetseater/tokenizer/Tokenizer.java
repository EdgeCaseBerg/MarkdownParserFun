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
        int openParenthesis = 0;
        for (int i = 1; i < tokens.size(); i++) {
            AbstractToken token = tokens.get(i);
            // Note: These Ifs can probably become a Switch statement later.
            if (token.equals(PoundToken.INSTANCE)) {
                AbstractToken previousToken = tokens.get(i - 1);
                boolean poundTokenOrNewline = previousToken.isOneOfType(PoundToken.TYPE, NewLineToken.TYPE);
                if (!poundTokenOrNewline) {
                    tokens.set(i, new TextToken(PoundToken.VALUE));
                }
            }
            // The only meaning a ( has, is if it's after a ]
            if (token.equals(ParenStartToken.INSTANCE)) {
                AbstractToken previousToken = tokens.get(i - 1);
                if (!previousToken.isOneOfType(BracketEndToken.TYPE)) {
                    tokens.set(i, new TextToken(ParenStartToken.VALUE));
                } else {
                    openParenthesis++;
                }
            }
            // The only meaning a ) has is if there's currently an open (
            if (token.equals(ParenStopToken.INSTANCE)) {
                if (openParenthesis == 0) {
                    tokens.set(i, new TextToken(ParenStopToken.VALUE));
                } else {
                    // We are closing a tag, so reduce the tokens backwards to the opening
                    // parenthesis into plain text and remove their meaning.
                    for (int j = i - 1; j > 0; j--) {
                        AbstractToken priorToken = tokens.get(j);
                        if (priorToken.equals(ParenStartToken.INSTANCE)) {
                            // Stop the loop.
                            break;
                        } else if (priorToken instanceof ConcreteToken concreteToken) {
                            // don't bother changing something that is already text.
                            if (concreteToken.getType().equals(TextToken.TYPE)) {
                                continue;
                            }
                            tokens.set(j, new TextToken(concreteToken.getValue()));
                        }
                    }
                    openParenthesis--;
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
