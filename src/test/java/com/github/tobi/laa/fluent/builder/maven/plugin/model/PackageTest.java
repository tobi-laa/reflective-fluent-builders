package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackageTest {

    @ParameterizedTest
    @MethodSource
    void testToString(final Package pack, final String expected) {
        // Act
        final String actual = pack.toString();
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testToString() {
        return Stream.of(
                Arguments.of(new Package(BigDecimal.class.getPackage()), "package java.math;"),
                Arguments.of(new Package(Test.class.getPackage()), "package org.junit.jupiter.api;"));
    }
}
