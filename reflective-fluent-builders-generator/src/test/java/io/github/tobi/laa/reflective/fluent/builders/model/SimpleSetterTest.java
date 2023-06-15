package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleSetterTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final var simpleSetter = SimpleSetter.builder() //
                .methodName("getSth") //
                .paramType(Map.class) //
                .paramName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .build();
        // Act
        final var withParamName = simpleSetter.withParamName(paramName);
        // Assert
        assertThat(withParamName).usingRecursiveComparison().isEqualTo(SimpleSetter.builder() //
                .methodName("getSth") //
                .paramType(Map.class) //
                .paramName(paramName) //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .build());
    }

    @ParameterizedTest
    @MethodSource
    void testEquals(final SimpleSetter a, final Object b, final boolean expected) {
        // Act
        final boolean actual = a.equals(b);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testEquals() {
        final var simpleSetter = SimpleSetter.builder() //
                .methodName("getSth") //
                .paramType(Map.class) //
                .paramName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .build();
        return Stream.of( //
                Arguments.of(simpleSetter, simpleSetter, true), //
                Arguments.of(simpleSetter, null, false), //
                Arguments.of(simpleSetter, "foobar", false), //
                Arguments.of( //
                        simpleSetter, //
                        ArraySetter.builder() //
                                .methodName("getSth") //
                                .paramType(Object[].class) //
                                .paramName("aName") //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .paramComponentType(Object.class) //
                                .build(), //
                        false));
    }
}