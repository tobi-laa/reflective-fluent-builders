package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleSetterTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final var simpleSetter = SimpleSetter.builder() //
                .methodName("getSth") //
                .propertyType(Map.class) //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .build();
        // Act
        final var withPropertyName = simpleSetter.withPropertyName(paramName);
        // Assert
        assertThat(withPropertyName).usingRecursiveComparison().isEqualTo(SimpleSetter.builder() //
                .methodName("getSth") //
                .propertyType(Map.class) //
                .propertyName(paramName) //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .build());
    }
}