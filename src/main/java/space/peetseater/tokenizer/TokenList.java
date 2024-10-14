package space.peetseater.tokenizer;

import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.tokens.NewLineToken;
import space.peetseater.tokenizer.tokens.TextToken;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class TokenList extends Vector<AbstractToken> {
    public TokenList(List<AbstractToken> tokens) {
        this.addAll(tokens);
    }

    public TokenList offset(int index) {
        if (index > size()) {
            return new TokenList(List.of());
        }
        if (index == 0) {
            return this;
        }

        return new TokenList(this.subList(index, this.size()));
    }

    public int nextNonWhitespaceOffset() {
        if (this.isEmpty()) {
            return 0;
        }
        boolean isWhitespace = true;
        Iterator<AbstractToken> iter = iterator();
        int offset = 0;
        while (isWhitespace && iter.hasNext()) {
            AbstractToken next = iter.next();
            offset++;
            switch (next.getType()) {
                case NewLineToken.TYPE ->
                    isWhitespace = true;
                case TextToken.TYPE ->
                    isWhitespace = ((TextToken)next).isEmpty();
                default -> {
                    isWhitespace = false;
                    offset--;
                }
            }
        }
        return offset;
    }

    public boolean typesAheadAre(String ...requiredTypesInARow) {
        if (this.isEmpty()) {
            return false;
        }

        int i = 0;
        for (String type : requiredTypesInARow) {
            if (i > size() - 1) {
                return false;
            }
            boolean matchesTypeAtIndex = get(i).getType().equals(type);
            if (!matchesTypeAtIndex) {
                return false;
            }
            i++;
        }

        if (i != requiredTypesInARow.length) {
            return false;
        }

        return true;
    }

    // TODO: add methods as needed
}
