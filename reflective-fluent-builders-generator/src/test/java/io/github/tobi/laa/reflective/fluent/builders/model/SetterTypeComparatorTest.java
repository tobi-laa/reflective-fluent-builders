package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.model.method.SetterTypeComparator;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.assertj.core.api.Assertions.assertThat;

class SetterTypeComparatorTest {

    private final SetterTypeComparator comparator = new SetterTypeComparator();

    @ParameterizedTest
    @MethodSource
    void testCompare(final Type a, final Type b, final int expected) {
        // Act
        final int actual = comparator.compare(a, b);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testCompare() {
        return Stream.of( //
                Arguments.of(null, null, -1), //
                Arguments.of(null, Long.class, -1), //
                Arguments.of(Long.class, null, 1), //
                Arguments.of(Long.class, Long.class, 0), //
                Arguments.of(Long.class, String.class, -7), //
                Arguments.of(String.class, Long.class, 7), //
                Arguments.of(Long.class, parameterize(Optional.class, Long.class), -9), //
                Arguments.of(Optional.class, parameterize(Optional.class, Long.class), -16), //
                Arguments.of(parameterize(Optional.class, Long.class), Optional.class, 16), //
                Arguments.of(parameterize(Optional.class, Long.class), parameterize(Optional.class, String.class), 0), //
                Arguments.of(parameterize(List.class, Long.class), parameterize(List.class, TypeUtils.wildcardType().withLowerBounds(Object.class).build()), 0));
    }

}