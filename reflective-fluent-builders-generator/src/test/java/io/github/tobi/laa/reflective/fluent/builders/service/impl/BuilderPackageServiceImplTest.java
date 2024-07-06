package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithBuilderExisting;
import io.github.tobi.laa.reflective.fluent.builders.test.models.full.Person;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.PACKAGE_PLACEHOLDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class BuilderPackageServiceImplTest {

    @InjectMocks
    private BuilderPackageServiceImpl service;

    @Mock
    private BuildersProperties properties;

    @Test
    void testResolveBuilderPackageNull() {
        // Arrange
        final Class<?> clazz = null;
        // Act
        final ThrowingCallable resolveBuilderPackage = () -> service.resolveBuilderPackage(clazz);
        // Assert
        assertThatThrownBy(resolveBuilderPackage).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(properties);
    }

    @ParameterizedTest
    @MethodSource
    void testResolveBuilderPackage(final Class<?> clazz, final String builderPackage, final String expected) {
        // Arrange
        doReturn(builderPackage).when(properties).getBuilderPackage();
        // Act
        final String actual = service.resolveBuilderPackage(clazz);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> testResolveBuilderPackage() {
        return Stream.of( //
                Arguments.of(SimpleClass.class, "a.fixed.package", "a.fixed.package"), //
                Arguments.of(ClassWithBuilderExisting.class, PACKAGE_PLACEHOLDER, ClassWithBuilderExisting.class.getPackage().getName()), //
                Arguments.of(Person.class, PACKAGE_PLACEHOLDER + ".builders", "io.github.tobi.laa.reflective.fluent.builders.test.models.full.builders"));
    }
}