package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FieldAccessorTest {

    @ParameterizedTest
    @MethodSource
    void testEquals(final FieldAccessor a, final Object b, final boolean expected) {
        // Act
        final boolean actual = a.equals(b);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> testEquals() {
        final FieldAccessor fieldAccessor = FieldAccessor.builder() //
                .propertyType(new SimpleType(String.class)) //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        return Stream.of( //
                Arguments.of(fieldAccessor, fieldAccessor, true), //
                Arguments.of(fieldAccessor, fieldAccessor.toBuilder().build(), true), //
                Arguments.of(fieldAccessor, null, false), //
                Arguments.of(fieldAccessor, "foobar", false), //
                Arguments.of( //
                        fieldAccessor, //
                        Setter.builder() //
                                .methodName("getSth") //
                                .propertyType(new ArrayType(Object[].class, Object.class)) //
                                .propertyName("aName") //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        false),
                Arguments.of( //
                        fieldAccessor, //
                        fieldAccessor.toBuilder().propertyName("anotherName").build(), //
                        false),
                Arguments.of( //
                        fieldAccessor, //
                        fieldAccessor.toBuilder().propertyType(new SimpleType(int.class)).build(), //
                        false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final FieldAccessor fieldAccessor = FieldAccessor.builder() //
                .propertyType(new SimpleType(String.class)) //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        // Act
        final FieldAccessor withParamName = fieldAccessor.withPropertyName(paramName);
        // Assert
        assertThat(withParamName).usingRecursiveComparison().isEqualTo(FieldAccessor.builder() //
                .propertyType(new SimpleType(String.class)) //
                .propertyName(paramName) //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .build());
    }
}