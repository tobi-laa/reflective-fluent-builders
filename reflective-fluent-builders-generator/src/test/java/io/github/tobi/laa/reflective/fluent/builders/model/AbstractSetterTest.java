package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractSetterTest {

    @ParameterizedTest
    @MethodSource
    void testEquals(final AbstractSetter a, final Object b, final boolean expected) {
        // Act
        final boolean actual = a.equals(b);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testEquals() {
        final var abstractSetter = TestSetter.builder() //
                .methodName("getSth") //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .build();
        return Stream.of( //
                Arguments.of(abstractSetter, abstractSetter, true), //
                Arguments.of(abstractSetter, null, false), //
                Arguments.of(abstractSetter, "foobar", false), //
                Arguments.of( //
                        abstractSetter, //
                        ArraySetter.builder() //
                                .methodName("getSth") //
                                .propertyType(Object[].class) //
                                .propertyName("aName") //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .paramComponentType(Object.class) //
                                .build(), //
                        false));
    }

    @SuperBuilder
    private static class TestSetter extends AbstractSetter {

        @Override
        public Type getPropertyType() {
            return Map.class;
        }

        @Override
        public TestSetter withPropertyName(final String paramName) {
            return this;
        }
    }
}
