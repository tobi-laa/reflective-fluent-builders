package io.github.tobi.laa.reflective.fluent.builders.mojo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExcludeValidatorTest {

    private final ExcludeValidator validator = new ExcludeValidator();

    @ParameterizedTest
    @MethodSource
    void isValidTrue(final Exclude exclude) {
        // Act
        final boolean actual = validator.isValid(exclude, null);
        // Assert
        assertTrue(actual);
    }

    static Stream<Exclude> isValidTrue() {
        return Stream.of( //
                null, //
                new Exclude(null, null, null, "notNull"), //
                new Exclude(null, null, "notNull", null), //
                new Exclude(null, "notNull", null, null), //
                new Exclude("notNull", null, null, null));
    }

    @ParameterizedTest
    @MethodSource
    void isValidFalse(final Exclude exclude) {
        // Act
        final boolean actual = validator.isValid(exclude, null);
        // Assert
        assertFalse(actual);
    }

    static Stream<Exclude> isValidFalse() {
        return Stream.of( //
                new Exclude(null, null, null, null), //
                new Exclude(null, null, "notNull", "notNull"), //
                new Exclude(null, "notNull", null, "notNull"), //
                new Exclude(null, "notNull", "notNull", null), //
                new Exclude(null, "notNull", "notNull", "notNull"), //
                new Exclude("notNull", null, null, "notNull"), //
                new Exclude("notNull", null, "notNull", null), //
                new Exclude("notNull", null, "notNull", "notNull"), //
                new Exclude("notNull", "notNull", null, null), //
                new Exclude("notNull", "notNull", null, "notNull"), //
                new Exclude("notNull", "notNull", "notNull", null), //
                new Exclude("notNull", "notNull", "notNull", "notNull"));
    }
}