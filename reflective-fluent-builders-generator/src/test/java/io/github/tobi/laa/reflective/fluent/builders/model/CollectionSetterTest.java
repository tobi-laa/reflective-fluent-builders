package io.github.tobi.laa.reflective.fluent.builders.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionSetterTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final CollectionSetter collectionSetter = CollectionSetter.builder()
                .methodName("getSth")
                .paramType(List.class)
                .paramName("aName")
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build();
        // Act
        final CollectionSetter withParamName = collectionSetter.withParamName(paramName);
        // Assert
        assertThat(withParamName).usingRecursiveComparison().isEqualTo(CollectionSetter.builder()
                .methodName("getSth")
                .paramType(List.class)
                .paramName(paramName)
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build());
    }
}