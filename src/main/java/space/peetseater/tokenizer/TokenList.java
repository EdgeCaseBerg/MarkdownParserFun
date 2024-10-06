package space.peetseater.tokenizer;

import space.peetseater.tokenizer.tokens.AbstractToken;

import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

public class TokenList extends Vector<AbstractToken> {
    public TokenList(List<AbstractToken> tokens) {
        this.addAll(tokens);
    }

    public void each(Consumer<AbstractToken> operation) {
        for (AbstractToken token : this) {
            operation.accept(token);
        }
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
