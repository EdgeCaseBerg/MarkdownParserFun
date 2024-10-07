package space.peetseater.parsing.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import space.peetseater.parsing.ast.*;
import space.peetseater.tokenizer.TokenList;
import space.peetseater.tokenizer.tokens.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListParserTest {

    ListParser listParser;

    @BeforeEach
    public void setup() {
        listParser = new ListParser();
    }

    @Test
    public void should_parse_dash_followed_by_text_and_newline_as_list() {
        AbstractMarkdownNode node = listParser.match(new TokenList(
                List.of(
                        DashToken.INSTANCE,
                        new TextToken(" a word"),
                        NewLineToken.INSTANCE
                )
        ));
        assertInstanceOf(ListNode.class, node);
        ListNode listNode = (ListNode) node;
        assertFalse(listNode.isOrdered());
    }

    @Test
    public void should_parse_more_than_one_item_as_part_of_list() {
        AbstractMarkdownNode node = listParser.match(new TokenList(
                List.of(
                        DashToken.INSTANCE,
                        new TextToken(" one"),
                        NewLineToken.INSTANCE,
                        DashToken.INSTANCE,
                        new TextToken(" two"),
                        NewLineToken.INSTANCE
                )
        ));
        assertInstanceOf(ListNode.class, node);
        ListNode listNode = (ListNode) node;
        assertFalse(listNode.isOrdered());
        assertEquals(2, listNode.getItems().size());
        assertInstanceOf(ListItemNode.class, listNode.getItems().get(0));
        assertInstanceOf(ListItemNode.class, listNode.getItems().get(1));
    }

}