package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.parsing.ast.CodeBlockNode;
import space.peetseater.parsing.ast.NullNode;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.BacktickToken;
import space.peetseater.tokenizer.tokens.ConcreteToken;

import java.util.LinkedList;

public class CodeBlockParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        if (tokenList.isEmpty()) {
            return NullNode.INSTANCE;
        }
        
        // ` ` ` text ` ` ` is at least 7 tokens so:
        if (tokenList.size() < 7) {
            return NullNode.INSTANCE;
        }

        if (!tokenList.typesAheadAre(BacktickToken.TYPE,BacktickToken.TYPE, BacktickToken.TYPE)) {
            return NullNode.INSTANCE;
        }
        
        TokenList afterTicks = tokenList.offset(3);
        LinkedList<AbstractToken> potentialCodeBlock = new LinkedList<>();
        boolean endingTicksFound = false;
        // Note: this doesn't handle the possibility of ``` inside of an ``` block, but I'm okay with that
        // for now because I'm pretty sure I've never done that ever in my markdown I'll be parsing.
        for (int i = 0; i < afterTicks.size(); i++) {
            AbstractToken token = afterTicks.get(i);
            if (token.getType().equals(BacktickToken.TYPE)) {
                // Do we have three in a row, indicating we should stop?
                int j = 1;
                boolean tokenIsBackTick = true;
                for (; j < 3 && i + j < afterTicks.size(); j++) {
                    tokenIsBackTick = tokenIsBackTick && afterTicks.get(i + j).getType().equals(BacktickToken.TYPE);
                }
                if (tokenIsBackTick) {
                    // Time to stop!
                    endingTicksFound = true;
                    break; // Skip because we don't want to add ` to the tokens.
                }
            }
            potentialCodeBlock.add(token);
        }

        if (endingTicksFound) {
            int consumed = 6; // our back ticks on either side.
            String code = collapseTokensToText(potentialCodeBlock);
            return new CodeBlockNode(code, consumed + potentialCodeBlock.size());
        }

        return NullNode.INSTANCE;
    }

    private String collapseTokensToText(LinkedList<AbstractToken> potentialCodeBlock) {
        if (potentialCodeBlock.isEmpty()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (AbstractToken token : potentialCodeBlock) {
            if (token instanceof ConcreteToken concreteToken) {
                stringBuilder.append(concreteToken.getValue());
            }
        }
        return stringBuilder.toString();
    }
}
