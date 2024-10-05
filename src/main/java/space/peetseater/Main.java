package space.peetseater;

import space.peetseater.tokenizer.AbstractToken;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.Tokenizer;

public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        TokenList tokens = tokenizer.tokenize("\nHello *world*\nHow _are_ you?");
        for (AbstractToken token : tokens) {
            System.out.println(token);
        }
    }
}