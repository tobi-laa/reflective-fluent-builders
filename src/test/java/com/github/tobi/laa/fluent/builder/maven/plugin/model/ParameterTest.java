package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterTest {

    @ParameterizedTest
    @MethodSource
    void testToString(final Parameter parameter, final String expected) {
        // Act
        final String actual = parameter.toString();
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testToString() {
        return Stream.of(
                Arguments.of(
                        new Parameter(false, BigDecimal.class, "myVar"),
                        "BigDecimal myVar"),
                Arguments.of(
                        new Parameter(true, Test.class, "test"),
                        "final Test test"));
    }
}
