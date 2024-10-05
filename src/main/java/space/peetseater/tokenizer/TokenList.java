package space.peetseater.tokenizer;

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

    // TODO: add methods as needed
}
