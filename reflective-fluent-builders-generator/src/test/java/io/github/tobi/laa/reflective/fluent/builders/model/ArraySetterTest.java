package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ArraySetterTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final var arraySetter = ArraySetter.builder() //
                .methodName("getSth") //
                .propertyType(Object[].class) //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .paramComponentType(Object.class) //
                .build();
        // Act
        final var withPropertyName = arraySetter.withPropertyName(paramName);
        // Assert
        assertThat(withPropertyName).usingRecursiveComparison().isEqualTo(ArraySetter.builder() //
                .methodName("getSth") //
                .propertyType(Object[].class) //
                .propertyName(paramName) //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .paramComponentType(Object.class) //
                .build());
    }
}