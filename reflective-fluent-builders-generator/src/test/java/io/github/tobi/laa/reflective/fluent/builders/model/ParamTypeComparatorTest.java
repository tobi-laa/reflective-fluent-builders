package io.github.tobi.laa.reflective.fluent.builders.model;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.assertj.core.api.Assertions.assertThat;

class ParamTypeComparatorTest {

    private final ParamTypeComparator comparator = new ParamTypeComparator();

    @ParameterizedTest
    @MethodSource
    void testCompare(final PropertyType a, final PropertyType b, final int expected) {
        // Act
        final int actual = comparator.compare(a, b);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testCompare() {
        return Stream.of( //
                Arguments.of(null, null, -1), //
                Arguments.of(null, new SimpleType(Long.class), -1), //
                Arguments.of(new SimpleType(Long.class), null, 1), //
                Arguments.of(new SimpleType(Long.class), new SimpleType(Long.class), 0), //
                Arguments.of(new SimpleType(Long.class), new SimpleType(String.class), -7), //
                Arguments.of(new SimpleType(String.class), new SimpleType(Long.class), 7), //
                Arguments.of(new SimpleType(Long.class), new SimpleType(parameterize(Optional.class, Long.class)), -9), //
                Arguments.of(new SimpleType(Optional.class), new SimpleType(parameterize(Optional.class, Long.class)), -16), //
                Arguments.of(new SimpleType(parameterize(Optional.class, Long.class)), new SimpleType(Optional.class), 16), //
                Arguments.of(new SimpleType(parameterize(Optional.class, Long.class)), new SimpleType(parameterize(Optional.class, String.class)), 0), //
                Arguments.of(
                        new CollectionType(
                                parameterize(List.class, Long.class),
                                Long.class),
                        new CollectionType(
                                parameterize(List.class, TypeUtils.wildcardType().withLowerBounds(Object.class).build()),
                                TypeUtils.wildcardType().withLowerBounds(Object.class).build()),
                        0));
    }

}