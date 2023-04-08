package io.github.tobi.laa.reflective.fluent.builders.mojo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IncludeValidatorTest {

    private final IncludeValidator validator = new IncludeValidator();

    @ParameterizedTest
    @MethodSource
    void isValidTrue(final Include include) {
        // Act
        final boolean actual = validator.isValid(include, null);
        // Assert
        assertTrue(actual);
    }

    private static Stream<Include> isValidTrue() {
        return Stream.of( //
                null, //
                new Include(null, "notNull"), //
                new Include("notNull", null));
    }

    @ParameterizedTest
    @MethodSource
    void isValidFalse(final Include include) {
        // Act
        final boolean actual = validator.isValid(include, null);
        // Assert
        assertFalse(actual);
    }

    private static Stream<Include> isValidFalse() {
        return Stream.of( //
                new Include(null, null), //
                new Include("notNull", "notNull"));
    }
}