package space.peetseater.tokenizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import space.peetseater.tokenizer.tokens.*;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenizerTest {

    Tokenizer tokenizer;
    @BeforeEach
    public void setup() {
        tokenizer = new Tokenizer();
    }

    @Test
    public void treat_token_as_text_in_middle_of_sentences() {
        TokenList tokens = tokenizer.tokenize("This is not a # Header");
        assertTrue(tokens.typesAheadAre(TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, EndOfFileToken.TYPE));
        assertEquals("This is not a ", tokens.get(0).getValue());
        assertEquals("#", tokens.get(1).getValue());
        assertEquals(" Header", tokens.get(2).getValue());
    }

    @ParameterizedTest()
    @MethodSource("stringToTokenListTestCases")
    public void string_of_x_should_become_tokens_list_of(String markdown, List<String> expectedTypes) {
        TokenList tokens = tokenizer.tokenize(markdown);
        List<String> actual = tokens.stream().map(AbstractToken::getType).toList();
        assertEquals(expectedTypes, actual);
    }

    public Stream<Arguments> stringToTokenListTestCases() {
        return Stream.of(
            Arguments.of(
                "\n# Header",
                List.of(NewLineToken.TYPE, PoundToken.TYPE, TextToken.TYPE, EndOfFileToken.TYPE)
            ),
            Arguments.of(
                "# Header",
                List.of(PoundToken.TYPE, TextToken.TYPE, EndOfFileToken.TYPE)
            ),
            Arguments.of(
                "[bla](a-link)",
                List.of(
                    BracketStartToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    ParenStartToken.TYPE,
                    TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, // a,-,link are all tokens with meaning removed.
                    ParenStopToken.TYPE,
                    EndOfFileToken.TYPE
                )
            ),
            Arguments.of(
                "[text][# is text][*bold*][_italics_][newline\nokay][(text)]",
                List.of(
                    BracketStartToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    BracketStartToken.TYPE, TextToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    BracketStartToken.TYPE, StarToken.TYPE, TextToken.TYPE, StarToken.TYPE, BracketEndToken.TYPE,
                    BracketStartToken.TYPE, UnderscoreToken.TYPE, TextToken.TYPE, UnderscoreToken.TYPE, BracketEndToken.TYPE,
                    BracketStartToken.TYPE, TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    BracketStartToken.TYPE, TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    EndOfFileToken.TYPE
                )
            ),
            Arguments.of(
                "text [ this is not a link [this is] and not this [",
                List.of(
                    TextToken.TYPE,
                    TextToken.TYPE, TextToken.TYPE,
                    BracketStartToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    TextToken.TYPE, TextToken.TYPE,
                    EndOfFileToken.TYPE
                )
            ),
            Arguments.of(
                " [foo]:bar",
                List.of(
                    TextToken.TYPE,
                    BracketStartToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    ColonToken.TYPE, TextToken.TYPE,
                    EndOfFileToken.TYPE
                )
            ),
            Arguments.of(
                "Equals and newline together retain meaning\n===\n\n",
                List.of(
                    TextToken.TYPE, NewLineToken.TYPE,
                    EqualsToken.TYPE, EqualsToken.TYPE, EqualsToken.TYPE, NewLineToken.TYPE,
                    NewLineToken.TYPE, EndOfFileToken.TYPE
                )
            ),
            Arguments.of(
                "This is just text = and not anything else",
                List.of(TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, EndOfFileToken.TYPE)
            ),
            Arguments.of(
                "Even if === has multiple, if there's no \n right after then its text",
                List.of(
                    TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, TextToken.TYPE, NewLineToken.TYPE,
                    TextToken.TYPE, EndOfFileToken.TYPE
                )
            ),
            Arguments.of(
                "a : on its own is just text",
                List.of(
                    TextToken.TYPE, TextToken.TYPE, TextToken.TYPE,
                    EndOfFileToken.TYPE
                )
            ),
            Arguments.of(
                " [foo]: not a valid link bar",
                List.of(
                    TextToken.TYPE,
                    BracketStartToken.TYPE, TextToken.TYPE, BracketEndToken.TYPE,
                    TextToken.TYPE, TextToken.TYPE,
                    EndOfFileToken.TYPE
                )
            )
        );
    }

}