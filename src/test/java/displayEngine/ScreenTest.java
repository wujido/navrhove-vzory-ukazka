package displayEngine;

import cz.wujido.shottrack.displayEngine.Screen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScreenTest {

    private static Stream<Arguments> appendTestData() {
        return Stream.of(
                Arguments.of(1, "first line"),
                Arguments.of(2, "first line\nsecond line"),
                Arguments.of(2, "first line\r\nsecond line"),
                Arguments.of(2, "first line\rsecond line"),
                Arguments.of(5, "1\r2\n3\r\n4\n5")
        );
    }

    @ParameterizedTest
    @DisplayName("Append should count lines in string")
    @MethodSource("appendTestData")
    void appendShouldCountLinesInString(int expectedCount, String testStr) {
        var screen = new Screen();
        screen.append(testStr);

        assertEquals(expectedCount, screen.Lines);
    }


    private static Stream<Arguments> appendLineTestData() {
        return Stream.of(
                Arguments.of(2, "first line"),
                Arguments.of(3, "first line\nsecond line"),
                Arguments.of(3, "first line\r\nsecond line"),
                Arguments.of(3, "first line\rsecond line"),
                Arguments.of(6, "1\r2\n3\r\n4\n5")
        );
    }

    @ParameterizedTest
    @DisplayName("Append line should add line")
    @MethodSource("appendLineTestData")
    void appendLineShouldAddLine(int expectedCount, String testStr) {
        var screen = new Screen();
        screen.appendLn(testStr);

        assertEquals(expectedCount, screen.Lines);
    }

    @Test
    @DisplayName("Freeze should make screen immutable")
    void freezeShouldMakeScreenImmutable() {
        var screen = new Screen();
        screen.append("content");
        screen.freeze();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> screen.append("after freeze"));
    }
}
