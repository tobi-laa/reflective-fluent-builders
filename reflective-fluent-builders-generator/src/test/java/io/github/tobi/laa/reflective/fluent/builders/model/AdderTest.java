package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AdderTest {

    @ParameterizedTest
    @ValueSource(strings = {"otherName", "yetAnotherName"})
    void testWithParamName(final String paramName) {
        // Arrange
        final Adder adder = Adder.builder() //
                .methodName("addName") //
                .propertyType(new CollectionType(List.class, String.class)) //
                .propertyName("names") //
                .paramName("name") //
                .paramType(new SimpleType(String.class)) //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        // Act
        final Adder withParamName = adder.withPropertyName(paramName);
        // Assert
        assertThat(withParamName).usingRecursiveComparison().isEqualTo(Adder.builder() //
                .methodName("addName") //
                .propertyType(new CollectionType(List.class, String.class)) //
                .propertyName(paramName) //
                .paramName("name") //
                .paramType(new SimpleType(String.class)) //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(ClassWithCollections.class) //
                .build());
    }
}
