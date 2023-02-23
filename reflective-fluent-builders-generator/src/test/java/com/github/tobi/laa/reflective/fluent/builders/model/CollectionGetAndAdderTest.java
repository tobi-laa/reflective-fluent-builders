package com.github.tobi.laa.reflective.fluent.builders.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionGetAndAdderTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final var collectionGetAndAdder = CollectionGetAndAdder.builder()
                .methodName("getSth")
                .paramType(List.class)
                .paramName("aName")
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build();
        // Act
        final var withParamName = collectionGetAndAdder.withParamName(paramName);
        // Assert
        assertThat(withParamName).usingRecursiveComparison().isEqualTo(CollectionGetAndAdder.builder()
                .methodName("getSth")
                .paramType(List.class)
                .paramName(paramName)
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build());
    }
}