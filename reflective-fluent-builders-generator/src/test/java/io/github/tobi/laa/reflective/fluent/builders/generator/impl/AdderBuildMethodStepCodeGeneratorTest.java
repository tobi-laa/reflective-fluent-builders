package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.service.api.WriteAccessorService;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@IntegrationTest
class AdderBuildMethodStepCodeGeneratorTest {

    @InjectMocks
    private AdderBuildMethodStepCodeGenerator generator;

    @Mock
    private WriteAccessorService writeAccessorService;

    @Test
    void testIsApplicableNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final ThrowingCallable isApplicable = () -> generator.isApplicable(writeAccessor);
        // Assert
        assertThatThrownBy(isApplicable).isInstanceOf(NullPointerException.class);
    }

    @Test
    void testIsApplicableNotAdder() {
        // Arrange
        final WriteAccessor writeAccessor = Getter.builder() //
                .methodName("getList") //
                .propertyName("list") //
                .propertyType(new CollectionType(List.class, String.class)) //
                .visibility(Visibility.PUBLIC) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        doReturn(false).when(writeAccessorService).isAdder(writeAccessor);
        // Act
        final boolean actual = generator.isApplicable(writeAccessor);
        // Assert
        assertThat(actual).isFalse();
    }

    @Test
    void testIsApplicable() {
        // Arrange
        final WriteAccessor writeAccessor = Adder.builder() //
                .methodName("addItem") //
                .propertyName("items") //
                .propertyType(new CollectionType(List.class, String.class)) //
                .paramName("item") //
                .paramType(new SimpleType(String.class)) //
                .visibility(Visibility.PUBLIC) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        doReturn(true).when(writeAccessorService).isAdder(writeAccessor);
        // Act
        final boolean actual = generator.isApplicable(writeAccessor);
        // Assert
        assertThat(actual).isTrue();
    }

    @Test
    void testGenerateNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final ThrowingCallable generate = () -> generator.generate(writeAccessor);
        // Assert
        assertThatThrownBy(generate).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void testGenerateNotApplicable() {
        // Arrange
        final WriteAccessor writeAccessor = Getter.builder() //
                .methodName("getList") //
                .propertyName("list") //
                .propertyType(new CollectionType(List.class, String.class)) //
                .visibility(Visibility.PUBLIC) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        doReturn(false).when(writeAccessorService).isAdder(writeAccessor);
        // Act
        final ThrowingCallable generate = () -> generator.generate(writeAccessor);
        // Assert
        assertThatThrownBy(generate)
                .isExactlyInstanceOf(CodeGenerationException.class)
                .message().contains("This generator is not applicable for ").contains("Getter");
    }

    @Test
    void testGenerate() {
        // Arrange
        final WriteAccessor writeAccessor = Adder.builder() //
                .methodName("addItem") //
                .propertyName("items") //
                .propertyType(new CollectionType(List.class, String.class)) //
                .paramName("item") //
                .paramType(new SimpleType(String.class)) //
                .visibility(Visibility.PUBLIC) //
                .declaringClass(ClassWithCollections.class) //
                .build();
        doReturn(true).when(writeAccessorService).isAdder(writeAccessor);
        // Act
        final CodeBlock expected = generator.generate(writeAccessor);
        // Assert
        assertThat(expected).hasToString("if (this.callSetterFor.items && this.fieldValue.items != null) {\n" +
                "  this.fieldValue.items.forEach(objectToBuild::addItem);\n" +
                "}\n");
    }
}
