package io.github.tobi.laa.reflective.fluent.builders.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionGetAndAdderTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final CollectionGetAndAdder collectionGetAndAdder = CollectionGetAndAdder.builder()
                .methodName("getSth")
                .paramType(List.class)
                .paramName("aName")
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build();
        // Act
        final CollectionGetAndAdder withParamName = collectionGetAndAdder.withParamName(paramName);
        // Assert
        assertThat(withParamName).usingRecursiveComparison().isEqualTo(CollectionGetAndAdder.builder()
                .methodName("getSth")
                .paramType(List.class)
                .paramName(paramName)
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build());
    }

    @Test
    void testCompareToEquivalentCollectionSetter() {
        // Arrange
        final var collectionGetAndAdder = CollectionGetAndAdder.builder()
                .methodName("getSth")
                .paramType(List.class)
                .paramName("aName")
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build();
        final var collectionSetter = CollectionSetter.builder()
                .methodName("setSth")
                .paramType(List.class)
                .paramName("aName")
                .visibility(Visibility.PRIVATE)
                .paramTypeArg(Object.class)
                .build();
        // Act
        final var compareToSetter = collectionGetAndAdder.compareTo(collectionSetter);
        final var compareFromSetter = collectionSetter.compareTo(collectionGetAndAdder);
        // Assert
        assertThat(compareToSetter).isNotZero().isEqualTo(compareFromSetter * -1);
    }
}