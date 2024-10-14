package space.peetseater;

import org.junit.jupiter.api.Test;

import space.peetseater.parsing.MarkdownParser;
import space.peetseater.parsing.ast.*;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.Tokenizer;
import static org.junit.jupiter.api.Assertions.*;

public class TokenizeAndParseTests {

    public void assertTypeAndValueOf(AbstractMarkdownNode node, Class<? extends MarkdownNode> expectedClass, String expectedValue) {
        assertInstanceOf(expectedClass, node);
        MarkdownNode realNode = expectedClass.cast(node);
        assertEquals(expectedValue, realNode.getValue());
    }

    @Test
    public void sample_text_from_ruby_blog_post() {
        String sampleFromRubyBlog = "__Foo__ and *text*.\n\nAnother para.";
        Tokenizer tokenizer = new Tokenizer();
        TokenList tokens = tokenizer.tokenize(sampleFromRubyBlog);
        MarkdownParser markdownParser = new MarkdownParser();
        AbstractMarkdownNode dom = markdownParser.parse(tokens);
        assertInstanceOf(BodyNode.class, dom);
        BodyNode root = (BodyNode) dom;

        AbstractMarkdownNode firstP = root.getBodyParts().get(0);
        assertInstanceOf(ParagraphNode.class, firstP);
        ParagraphNode p1 = (ParagraphNode) firstP;
        assertTypeAndValueOf(p1.getSentences().get(0), ItalicsNode.class, "Foo");
        assertTypeAndValueOf(p1.getSentences().get(1), TextNode.class, " and ");
        assertTypeAndValueOf(p1.getSentences().get(2), BoldNode.class, "text");

        AbstractMarkdownNode secondP = root.getBodyParts().get(1);
        assertInstanceOf(ParagraphNode.class, secondP);
        ParagraphNode p2 = (ParagraphNode) secondP;
        assertTypeAndValueOf(p2.getSentences().get(0), TextNode.class, "Another para.");
    }
}
