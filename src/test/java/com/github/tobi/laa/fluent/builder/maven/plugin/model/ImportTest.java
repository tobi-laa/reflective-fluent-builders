package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ImportTest {

    @ParameterizedTest
    @MethodSource
    void testCompareTo(final Import fragment1, final Import fragment2, final int expected) {
        // Act
        final int actual = fragment1.compareTo(fragment2);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testCompareTo() {
        return Stream.of(
                Arguments.of(
                        new Import(BigDecimal.class, false),
                        null,
                        1),
                Arguments.of(
                        new Import(BigDecimal.class, false),
                        new Import(BigDecimal.class, false),
                        0),
                Arguments.of(
                        new Import(BigDecimal.class, true),
                        new Import(BigDecimal.class, false),
                        -1),
                Arguments.of(
                        new Import(BigDecimal.class, false),
                        new Import(Test.class, false),
                        -5),
                Arguments.of(
                        new Import(BigDecimal.class, false),
                        new Import(Test.class, true),
                        1));
    }

    @ParameterizedTest
    @MethodSource
    void testToString(final Import fragment, final String expected) {
        // Act
        final String actual = fragment.toString();
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testToString() {
        return Stream.of(
                Arguments.of(new Import(BigDecimal.class, false), "import java.math.BigDecimal;"),
                Arguments.of(new Import(BigDecimal.class, true), "import static java.math.BigDecimal;"),
                Arguments.of(new Import(Test.class, false), "import org.junit.jupiter.api.Test;"));
    }
}