package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.google.common.collect.ImmutableSet;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Type;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.apache.commons.lang3.reflect.TypeUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TypeServiceImplTest {

    private final TypeServiceImpl service = new TypeServiceImpl();

    @Test
    void testExplodeTypeNull() {
        // Act
        final Executable explodeType = () -> service.explodeType(null);
        // Assert
        assertThrows(NullPointerException.class, explodeType);
    }

    @ParameterizedTest
    @ValueSource(classes = {int.class, long.class, String.class, Long.class, Object.class, List.class})
    void testExplodeTypeSimple(final Class<?> type) {
        // Act
        final Set<Class<?>> actual = service.explodeType(type);
        // Assert
        assertThat(actual).containsExactly(type);
    }

    @ParameterizedTest
    @MethodSource
    void testExplodeTypeWildcard(final Type type, final Set<Class<?>> expected) {
        // Act
        final Set<Class<?>> actual = service.explodeType(type);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testExplodeTypeWildcard() {
        return Stream.of( //
                Arguments.of( //
                        wildcardType().withLowerBounds(Number.class).build(), //
                        ImmutableSet.of(Number.class)), //
                Arguments.of( //
                        wildcardType().withUpperBounds(String.class).build(), //
                        ImmutableSet.of(String.class)), //
                Arguments.of( //
                        wildcardType().withLowerBounds(Number.class).withUpperBounds(String.class).build(), //
                        ImmutableSet.of(Number.class, String.class)));
    }

    @ParameterizedTest
    @MethodSource
    void testExplodeTypeTypeVariable(final Type type, final Set<Class<?>> expected) {
        // Act
        final Set<Class<?>> actual = service.explodeType(type);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testExplodeTypeTypeVariable() {
        return Stream.of( //
                Arguments.of(typeVariable("r"), ImmutableSet.of(Object.class)), //
                Arguments.of(typeVariable("s"), ImmutableSet.of(Number.class)), //
                Arguments.of(typeVariable("t"), ImmutableSet.of(String.class)), //
                Arguments.of(typeVariable("u"), ImmutableSet.of(Comparable.class)), //
                Arguments.of(typeVariable("v"), ImmutableSet.of(Comparable.class)));
    }

    @SneakyThrows
    private static Type typeVariable(final String fieldName) {
        return TypeVariables.class.getDeclaredField(fieldName).getGenericType();
    }

    @ParameterizedTest
    @MethodSource
    void testExplodeTypeParameterizedType(final Type type, final Set<Class<?>> expected) {
        // Act
        final Set<Class<?>> actual = service.explodeType(type);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testExplodeTypeParameterizedType() {
        return Stream.of( //
                // constructed type is List<String>
                Arguments.of( //
                        parameterize(List.class, String.class), //
                        ImmutableSet.of(List.class, String.class)), //
                // constructed type is Map<Long, Boolean>
                Arguments.of( //
                        parameterize(Map.class, Long.class, Boolean.class), //
                        ImmutableSet.of(Map.class, Long.class, Boolean.class)), //
                // constructed type is Set<Number>[]
                Arguments.of( //
                        genericArrayType( //
                                parameterize(Set.class, Number.class)), //
                        ImmutableSet.of(Set.class, Number.class)), //
                // constructed type is List<Map<Long, Set<Deque<Number>[]>>>
                Arguments.of( //
                        parameterize( //
                                List.class, //
                                parameterize( //
                                        Map.class, //
                                        Long.class, //
                                        parameterize( //
                                                Set.class, //
                                                genericArrayType( //
                                                        parameterize(Deque.class, Number.class))))), //
                        ImmutableSet.of(List.class, Map.class, Long.class, Set.class, Deque.class, Number.class)));
    }

    @SuppressWarnings("unused")
    private static class TypeVariables<R, S extends Number, T extends String, U extends Comparable<U>, V extends U> {

        private R r;

        private S s;

        private T t;

        private U u;

        private V v;
    }
}