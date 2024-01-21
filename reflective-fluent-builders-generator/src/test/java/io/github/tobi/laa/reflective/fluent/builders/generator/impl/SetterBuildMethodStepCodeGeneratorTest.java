package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.model.SimpleType;
import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
import io.github.tobi.laa.reflective.fluent.builders.service.api.WriteAccessorService;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SetterBuildMethodStepCodeGeneratorTest {

    @InjectMocks
    private SetterBuildMethodStepCodeGenerator generator;

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

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testIsApplicable(final boolean expected) {
        // Arrange
        final WriteAccessor writeAccessor = Setter.builder() //
                .methodName("setSth") //
                .propertyName("sth") //
                .propertyType(new SimpleType(String.class)) //
                .visibility(Visibility.PUBLIC) //
                .declaringClass(SimpleClass.class) //
                .build();
        doReturn(expected).when(writeAccessorService).isSetter(any());
        // Act
        final boolean actual = generator.isApplicable(writeAccessor);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testGenerateNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final ThrowingCallable generate = () -> generator.generate(writeAccessor);
        // Assert
        assertThatThrownBy(generate).isInstanceOf(NullPointerException.class);
    }

    @Test
    void testGenerateCodeGenerationException() {
        // Arrange
        final WriteAccessor writeAccessor = Setter.builder() //
                .methodName("setSth") //
                .propertyName("sth") //
                .propertyType(new SimpleType(String.class)) //
                .visibility(Visibility.PUBLIC) //
                .declaringClass(SimpleClass.class) //
                .build();
        doReturn(false).when(writeAccessorService).isSetter(any());
        // Act
        final ThrowingCallable generate = () -> generator.generate(writeAccessor);
        // Assert
        assertThatThrownBy(generate)
                .isInstanceOf(CodeGenerationException.class)
                .message().contains(writeAccessor.getClass().getSimpleName());
    }
}
