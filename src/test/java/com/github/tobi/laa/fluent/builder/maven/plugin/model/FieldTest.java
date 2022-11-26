package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldTest {

    @ParameterizedTest
    @MethodSource
    void testCompareTo(final Field field1, final Field field2, final int expected) {
        // Act
        final int actual = field1.compareTo(field2);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testCompareTo() {
        return Stream.of(
                Arguments.of(
                        new Field(PRIVATE, BigDecimal.class, "myVar"),
                        null,
                        1),
                Arguments.of(
                        new Field(PRIVATE, BigDecimal.class, "myVar"),
                        new Field(PRIVATE, BigDecimal.class, "myVar"),
                        0),
                Arguments.of(
                        new Field(PRIVATE | STATIC, BigDecimal.class, "myVar"),
                        new Field(PRIVATE, BigDecimal.class, "myVar"),
                        -1),
                Arguments.of(
                        new Field(PRIVATE, BigDecimal.class, "myVar"),
                        new Field(PRIVATE, BigDecimal.class, "yourVar"),
                        -12));
    }

    @ParameterizedTest
    @MethodSource
    void testToString(final Field field, final String expected) {
        // Act
        final String actual = field.toString();
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testToString() {
        return Stream.of(
                Arguments.of(
                        new Field(PRIVATE, BigDecimal.class, "myVar"),
                        "    private BigDecimal myVar;"),
                Arguments.of(
                        new Field(PUBLIC | STATIC, BigDecimal.class, "bigDecimal"),
                        "    public static BigDecimal bigDecimal;"),
                Arguments.of(
                        new Field(FINAL,  Test.class, "test"),
                        "    final Test test;"));
    }
}
