package space.peetseater.parsing.parsers;

import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.tokenizer.TokenList;

public class SentenceParser extends TokenParser {
    @Override
    public AbstractMarkdownNode match(TokenList tokenList) {
        return matchFirst(tokenList, new ItalicsParser(), new BoldParser(), new TextParser());
    }
}
