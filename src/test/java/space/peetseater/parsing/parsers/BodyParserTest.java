package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.*;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BodyParserTest {

    BodyParser bodyParser;

    @BeforeEach
    public void setup() {
        bodyParser = new BodyParser();
    }

    @Test
    public void handles_empty_token_list_as_null() {
        AbstractMarkdownNode node = bodyParser.match(new TokenList(List.of()));
        assertEquals(NullNode.INSTANCE, node);
    }

    @Test
    public void parses_multiple_paragraphs_as_body() {
        AbstractMarkdownNode node = bodyParser.match(new TokenList(List.of(
                StarToken.INSTANCE, new TextToken("Heading"), StarToken.INSTANCE,
                NewLineToken.INSTANCE, NewLineToken.INSTANCE,
                UnderscoreToken.INSTANCE, new TextToken("Ready?"), UnderscoreToken.INSTANCE,
                NewLineToken.INSTANCE, NewLineToken.INSTANCE,
                new TextToken("Thanks for testing this code!"),
                EndOfFileToken.INSTANCE
        )));
        assertNotEquals(NullNode.INSTANCE, node);
        assertInstanceOf(BodyNode.class, node);
        BodyNode bodyNode = (BodyNode) node;
        AbstractMarkdownNode heading = bodyNode.getBodyParts().get(0);
        assertInstanceOf(ParagraphNode.class, heading);
        assertInstanceOf(BoldNode.class, ((ParagraphNode)heading).getSentences().get(0));

        AbstractMarkdownNode italics = bodyNode.getBodyParts().get(1);
        assertInstanceOf(ParagraphNode.class, italics);
        assertInstanceOf(ItalicsNode.class, ((ParagraphNode)italics).getSentences().get(0));

        AbstractMarkdownNode textToken = bodyNode.getBodyParts().get(2);
        assertInstanceOf(ParagraphNode.class, textToken);
        assertInstanceOf(TextNode.class, ((ParagraphNode)textToken).getSentences().get(0));

        assertEquals(3, bodyNode.getBodyParts().size());
    }

    @Test
    public void parses_lists_as_body() {
        AbstractMarkdownNode node = bodyParser.match(new TokenList(List.of(
                DashToken.INSTANCE, new TextToken("1st list item no 1"), NewLineToken.INSTANCE,
                DashToken.INSTANCE, new TextToken("1st list item no 2"), NewLineToken.INSTANCE,
                DashToken.INSTANCE, new TextToken("1st list item no 3"), NewLineToken.INSTANCE,
                NewLineToken.INSTANCE,
                new TextToken("Paragraph"),
                NewLineToken.INSTANCE, NewLineToken.INSTANCE,
                DashToken.INSTANCE, new TextToken("2nd list 1 item"), NewLineToken.INSTANCE,
                DashToken.INSTANCE, new TextToken("2nd list 2 item"), NewLineToken.INSTANCE,
                DashToken.INSTANCE, new TextToken("2nd list 3 item"), NewLineToken.INSTANCE,
                EndOfFileToken.INSTANCE
        )));
        assertNotEquals(NullNode.INSTANCE, node);
        assertInstanceOf(BodyNode.class, node);
        BodyNode bodyNode = (BodyNode) node;
        AbstractMarkdownNode listOne = bodyNode.getBodyParts().get(0);
        assertInstanceOf(ListNode.class, listOne);

        assertEquals(3, bodyNode.getBodyParts().size());
        AbstractMarkdownNode p = bodyNode.getBodyParts().get(1);
        assertInstanceOf(ParagraphNode.class, p);

        AbstractMarkdownNode listTwo = bodyNode.getBodyParts().get(2);
        assertInstanceOf(ListNode.class, listTwo);

    }


}