package space.peetseater;

import space.peetseater.generators.html.HtmlGenerator;
import space.peetseater.parsing.MarkdownParser;
import space.peetseater.parsing.ast.AbstractMarkdownNode;
import space.peetseater.tokenizer.tokens.AbstractToken;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.Tokenizer;

public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        String sampleInput = "# Heading\n\nHello `code` how are you";
        TokenList tokens = tokenizer.tokenize(sampleInput);
        for (AbstractToken token : tokens) {
            System.out.println(token);
        }
        MarkdownParser markdownParser = new MarkdownParser();
        AbstractMarkdownNode dom = markdownParser.parse(tokens);
        System.out.println(dom);

        HtmlGenerator htmlGenerator = new HtmlGenerator();
        String htmlString = htmlGenerator.generate(dom);
        System.out.println(htmlString);
    }
}