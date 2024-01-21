package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionSetterTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final var collectionSetter = CollectionSetter.builder() //
                .methodName("getSth") //
                .propertyType(List.class) //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .paramTypeArg(Object.class) //
                .build();
        // Act
        final var withPropertyName = collectionSetter.withPropertyName(paramName);
        // Assert
        assertThat(withPropertyName).usingRecursiveComparison().isEqualTo(CollectionSetter.builder() //
                .methodName("getSth") //
                .propertyType(List.class) //
                .propertyName(paramName) //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .paramTypeArg(Object.class) //
                .build());
    }
}