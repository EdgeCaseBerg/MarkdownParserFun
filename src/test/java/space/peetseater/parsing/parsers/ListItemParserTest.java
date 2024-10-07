package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.*;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListItemParserTest {

    ListItemParser listItemParser;

    @BeforeEach
    public void setup() {
         listItemParser = new ListItemParser();
    }

    @Test
    public void should_parse_dash_followed_by_text_and_newline_as_list_item() {
        AbstractMarkdownNode node = listItemParser.match(new TokenList(
                List.of(
                        DashToken.INSTANCE,
                        new TextToken(" a word"),
                        NewLineToken.INSTANCE
                )
        ));
        assertInstanceOf(ListItemNode.class, node);
    }

    @Test
    public void should_parse_dash_followed_by_bold_text_and_newline_as_list_item() {
        AbstractMarkdownNode node = listItemParser.match(new TokenList(
                List.of(
                        DashToken.INSTANCE,
                        StarToken.INSTANCE,
                        new TextToken("bold"),
                        StarToken.INSTANCE,
                        new TextToken(" text"),
                        NewLineToken.INSTANCE
                )
        ));
        assertInstanceOf(ListItemNode.class, node);
        ListItemNode listItemNode = (ListItemNode) node;
        assertTrue(listItemNode.isOrdered());
        assertEquals(2, listItemNode.getRuns().size());
        assertInstanceOf(BoldNode.class, listItemNode.getRuns().get(0));
        assertInstanceOf(TextNode.class, listItemNode.getRuns().get(1));
    }

    @Test
    public void should_parse_dash_followed_by_italics_text_and_newline_as_list_item() {
        AbstractMarkdownNode node = listItemParser.match(new TokenList(
                List.of(
                        DashToken.INSTANCE,
                        UnderscoreToken.INSTANCE,
                        new TextToken(" emphasis "),
                        UnderscoreToken.INSTANCE,
                        new TextToken(" text"),
                        NewLineToken.INSTANCE
                )
        ));
        assertInstanceOf(ListItemNode.class, node);
        ListItemNode listItemNode = (ListItemNode) node;
        assertTrue(listItemNode.isOrdered());
        assertEquals(2, listItemNode.getRuns().size());
        assertInstanceOf(ItalicsNode.class, listItemNode.getRuns().get(0));
        assertInstanceOf(TextNode.class, listItemNode.getRuns().get(1));
    }


}