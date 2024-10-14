package space.peetseater.tokenizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import space.peetseater.tokenizer.scanners.SimpleScanner;
import space.peetseater.tokenizer.tokens.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleScannerTest {

    @Test
    public void empty_strings_tokenize_to_null_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("");
        assertEquals(NullToken.INSTANCE, token);
    }

    @Test
    public void strings_not_matching_known_symbols_tokenize_to_null_token() {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString("foo bar baz");
        assertEquals(NullToken.INSTANCE, token);
    }

    @ParameterizedTest
    @MethodSource("provideScannenTokenTestCases")
    void test_token_literal_becomes_token_type(String input, ConcreteToken expectedToken) {
        SimpleScanner scanner = new SimpleScanner();
        AbstractToken token = scanner.fromString(input);
        assertEquals(expectedToken, token);
    }

    public static Stream<Arguments> provideScannenTokenTestCases() {
        return Stream.of(
                Arguments.of("<", AngleStartToken.INSTANCE),
                Arguments.of(">", AngleStopToken.INSTANCE),
                Arguments.of("`", BacktickToken.INSTANCE),
                Arguments.of("[", BracketStartToken.INSTANCE),
                Arguments.of("]", BracketEndToken.INSTANCE),
                Arguments.of("-", DashToken.INSTANCE),
                Arguments.of("=", EqualsToken.INSTANCE),
                Arguments.of("\\", EscapeCharacterToken.INSTANCE),
                Arguments.of("\n", NewLineToken.INSTANCE),
                Arguments.of("(", ParenStartToken.INSTANCE),
                Arguments.of(")", ParenStopToken.INSTANCE),
                Arguments.of("#", PoundToken.INSTANCE),
                Arguments.of("*", StarToken.INSTANCE),
                Arguments.of("_", UnderscoreToken.INSTANCE),
                Arguments.of(":", ColonToken.INSTANCE)
        );
    }

}