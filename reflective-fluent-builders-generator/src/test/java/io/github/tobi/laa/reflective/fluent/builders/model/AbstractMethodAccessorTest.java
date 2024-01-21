package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractMethodAccessorTest {

    @ParameterizedTest
    @MethodSource
    void testEquals(final AbstractMethodAccessor a, final Object b, final boolean expected) {
        // Act
        final boolean actual = a.equals(b);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testEquals() {
        final var abstractSetter = TestAccessor.builder() //
                .propertyType(new MapType(Map.class, Object.class, Object.class)) //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .methodName("foobar") //
                .build();
        return Stream.of( //
                Arguments.of(abstractSetter, abstractSetter, true), //
                Arguments.of(abstractSetter, null, false), //
                Arguments.of(abstractSetter, "foobar", false), //
                Arguments.of( //
                        abstractSetter, //
                        Setter.builder() //
                                .methodName("getSth") //
                                .propertyType(new ArrayType(Object[].class, Object.class)) //
                                .propertyName("aName") //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        false));
    }

    @SuperBuilder
    private static class TestAccessor extends AbstractMethodAccessor {

        @Override
        public TestAccessor withPropertyName(final String paramName) {
            return this;
        }
    }
}
