package com.github.tobi.laa.reflective.fluent.builders.generator.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ArraySetterTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final var arraySetter = ArraySetter.builder()
                .methodName("getSth")
                .paramType(Object[].class)
                .paramName("aName")
                .visibility(Visibility.PRIVATE)
                .paramComponentType(Object.class)
                .build();
        // Act
        final var withParamName = arraySetter.withParamName(paramName);
        // Assert
        assertThat(withParamName).usingRecursiveComparison().isEqualTo(ArraySetter.builder()
                .methodName("getSth")
                .paramType(Object[].class)
                .paramName(paramName)
                .visibility(Visibility.PRIVATE)
                .paramComponentType(Object.class)
                .build());
    }
}