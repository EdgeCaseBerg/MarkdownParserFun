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
            collapsePoundTokenIfNeeded(tokens, token, i);
            openParenthesis += collapseParenStartTokenIfNeeded(tokens, token, i);
            openParenthesis -= collapseParentStopTokenIfNeeded(tokens, token, i, openParenthesis);
            collapseMeaninglessBrackets(tokens, token, i);
            collapseColonsThatAreNotPartOfDefinitions(tokens, token, i);
            i = collapseEqualsIfNotUsedOrAdvanceIterationBy(tokens, token, i);
            // collapse - that aren't at least 3 --- long and arent followed by a newline
        }
        return tokens;
    }

    private int collapseEqualsIfNotUsedOrAdvanceIterationBy(List<AbstractToken> tokens, AbstractToken token, int i) {
        if (!token.isOneOfType(EqualsToken.TYPE)) {
            return i;
        }
        int lastEqualsIndex = i;
        for (int j = i + 1; j < tokens.size(); j++) {
            if (!tokens.get(j).isOneOfType(EqualsToken.TYPE)) {
                lastEqualsIndex = j;
                break;
            }
        }
        // If we advanced forward, check if there's a newline next
        if (lastEqualsIndex != i) {
            if (tokens.get(lastEqualsIndex).isOneOfType(NewLineToken.TYPE)) {
                // These are real tokens with meaning. Leave them alone and advance
                // i forward past all of them.
                return lastEqualsIndex;
            } else {
                // convert all of the newlines we saw into plain text.
                for (int j = i; j < lastEqualsIndex; j++) {
                    tokens.set(j, new TextToken(tokens.get(j).getValue()));
                }
            }
        }
        return i;
    }

    private void collapseColonsThatAreNotPartOfDefinitions(List<AbstractToken> tokens, AbstractToken token, int i) {
        if (token.isOneOfType(ColonToken.TYPE)) {
            // Does this come after a ] ?
            AbstractToken previous = tokens.get(i - 1);
            boolean hasBracketBehind = previous.isOneOfType(BracketEndToken.TYPE);
            if (!hasBracketBehind) {
                // If not this is just a colon in text somewhere.
                tokens.set(i, new TextToken(token.getValue()));
            }
            if (i + 1 < tokens.size() && tokens.get(i + 1).isOneOfType(TextToken.TYPE)) {
                TextToken next = (TextToken) tokens.get(i + 1);
                boolean whiteSpaceAfterColon = next.getValue().startsWith(" ");
                if (whiteSpaceAfterColon) {
                    tokens.set(i, new TextToken(token.getValue()));
                }
            }
        }
    }

    private void collapseMeaninglessBrackets(List<AbstractToken> tokens, AbstractToken token, int i) {
        // If it's a bracket, then normalize the text in between.
        // But leave the inline formatting such as bold, italics, underline alone
        if (!token.isOneOfType(BracketStartToken.TYPE)) {
            return;
        }
        // Scan forward to find the end bracket
        int endingBracketIndex = i;
        for (int j = i + 1; j < tokens.size(); j++) {
            if (tokens.get(j).isOneOfType(BracketEndToken.TYPE)) {
                endingBracketIndex = j;
                break;
            }
            if (tokens.get(j).isOneOfType(BracketStartToken.TYPE)) {
                // Wait, [ [ ? the first of these cannot be a link indicator.
                // Unless I want to enable something like [link[]] but I don't think I have that case
                break;
            }
        }
        if (endingBracketIndex != i) {
            // If we found the ending bracket then normalize the text between
            removeNonFormatRelatedTokensFromRange(i, endingBracketIndex, tokens);
        } else {
            // Otherwise, this bracket is just a random stand alone bracket doing nothing.
            tokens.set(i, new TextToken(token.getValue()));
        }
    }

    private void removeNonFormatRelatedTokensFromRange(int startExclusive, int endingExclusive, List<AbstractToken> tokens) {
        for (int i = startExclusive + 1; i < endingExclusive; i++) {
            AbstractToken tokenToCheck = tokens.get(i);
            if (tokenToCheck.isOneOfType(
                AngleStartToken.TYPE,
                AngleStopToken.TYPE,
                ColonToken.TYPE,
                DashToken.TYPE,
                EqualsToken.TYPE,
                NewLineToken.TYPE,
                ParenStartToken.TYPE,
                ParenStopToken.TYPE
            )) {
                tokens.set(i, new TextToken(tokenToCheck.getValue()));
            }
        }
    }

    private int collapseParentStopTokenIfNeeded(List<AbstractToken> tokens, AbstractToken token, int i, int openParenthesis) {
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
                return 1;
            }
        }
        return 0;
    }

    private int collapseParenStartTokenIfNeeded(List<AbstractToken> tokens, AbstractToken token, int i) {
        // The only meaning a ( has, is if it's after a ]
        if (!token.equals(ParenStartToken.INSTANCE)) {
            return 0;
        }

        AbstractToken previousToken = tokens.get(i - 1);
        if (!previousToken.isOneOfType(BracketEndToken.TYPE)) {
            tokens.set(i, new TextToken(ParenStartToken.VALUE));
            return 0;
        } else {
            return 1;
        }
    }

    private static void collapsePoundTokenIfNeeded(List<AbstractToken> tokens, AbstractToken token, int i) {
        if (!token.equals(PoundToken.INSTANCE)) {
            return;
        }
        AbstractToken previousToken = tokens.get(i - 1);
        boolean poundTokenOrNewline = previousToken.isOneOfType(PoundToken.TYPE, NewLineToken.TYPE);
        if (!poundTokenOrNewline) {
            tokens.set(i, new TextToken(PoundToken.VALUE));
        }
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
