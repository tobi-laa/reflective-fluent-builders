package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.service.api.JavaClassService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.TypeService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivateConstructor;
import io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.SettersWithDifferentVisibility;
import lombok.SneakyThrows;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.*;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessibilityServiceImplTest {

    @InjectMocks
    private AccessibilityServiceImpl accessibilityService;

    @Mock
    private VisibilityService visibilityService;

    @Mock
    private TypeService typeService;

    @Mock
    private JavaClassService javaClassService;

    @ParameterizedTest
    @MethodSource
    void testIsClassAccessibleFromNull(final Class<?> clazz, final String packageName) {
        // Act
        final ThrowingCallable isAccessibleFrom = () -> accessibilityService.isAccessibleFrom(clazz, packageName);
        // Assert
        assertThatThrownBy(isAccessibleFrom).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(visibilityService, typeService, javaClassService);
    }

    private static Stream<Arguments> testIsClassAccessibleFromNull() {
        return Stream.of( //
                Arguments.of(null, null), //
                Arguments.of(String.class, null), //
                Arguments.of(null, "a.package.name"));
    }

    @ParameterizedTest
    @MethodSource
    void testIsClassAccessibleFrom(final Class<?> clazz, final String packageName, final Visibility visibility, final boolean expected) {
        // Arrange
        doReturn(visibility).when(visibilityService).toVisibility(anyInt());
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(clazz, packageName);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testIsClassAccessibleFrom() {
        return Stream.of( //
                Arguments.of(String.class, "java.lang", PRIVATE, false), //
                Arguments.of(String.class, "java.lang", Visibility.PACKAGE_PRIVATE, true), //
                Arguments.of(String.class, "java.lang", Visibility.PROTECTED, true), //
                Arguments.of(String.class, "java.lang", PUBLIC, true), //
                //
                Arguments.of(ClassLoader.class, "java.lang", PRIVATE, false), //
                Arguments.of(ClassLoader.class, "java.lang", Visibility.PACKAGE_PRIVATE, false), //
                Arguments.of(ClassLoader.class, "java.lang", Visibility.PROTECTED, true), //
                Arguments.of(ClassLoader.class, "java.lang", PUBLIC, true),
                //
                Arguments.of(String.class, "a.weird.package", PRIVATE, false), //
                Arguments.of(String.class, "a.weird.package", Visibility.PACKAGE_PRIVATE, false), //
                Arguments.of(String.class, "a.weird.package", Visibility.PROTECTED, false), //
                Arguments.of(String.class, "a.weird.package", PUBLIC, true), //
                //
                Arguments.of(ClassLoader.class, "a.weird.package", PRIVATE, false), //
                Arguments.of(ClassLoader.class, "a.weird.package", Visibility.PACKAGE_PRIVATE, false), //
                Arguments.of(ClassLoader.class, "a.weird.package", Visibility.PROTECTED, false), //
                Arguments.of(ClassLoader.class, "a.weird.package", PUBLIC, true));
    }

    @ParameterizedTest
    @MethodSource
    void testIsTypeAccessibleFromNull(final Type type, final String packageName) {
        // Act
        final ThrowingCallable isAccessibleFrom = () -> accessibilityService.isAccessibleFrom(type, packageName);
        // Assert
        assertThatThrownBy(isAccessibleFrom).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(visibilityService, typeService, javaClassService);
    }

    private static Stream<Arguments> testIsTypeAccessibleFromNull() {
        return Stream.of( //
                Arguments.of(null, null), //
                Arguments.of(String.class, null), //
                Arguments.of(null, "a.package.name"));
    }

    @Test
    @SneakyThrows
    void testIsTypeAccessibleFromNotAllExplodedTypesAccessible() {
        // Arrange
        final Type type = Object.class;
        final var packagePrivate = Class.forName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivate");
        final var packageName = "java.lang";
        doReturn(Set.of(String.class, packagePrivate)).when(typeService).explodeType(type);
        lenient().doReturn(PUBLIC).when(visibilityService).toVisibility(String.class.getModifiers());
        doReturn(PACKAGE_PRIVATE).when(visibilityService).toVisibility(packagePrivate.getModifiers());
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(type, packageName);
        // Assert
        assertThat(actual).isFalse();
    }

    @Test
    @SneakyThrows
    void testIsTypeAccessibleFrom() {
        // Arrange
        final Type type = Object.class;
        final var packagePrivate = Class.forName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivate");
        final var packageName = "java.lang";
        doReturn(Set.of(String.class, packagePrivate)).when(typeService).explodeType(type);
        doReturn(PUBLIC).when(visibilityService).toVisibility(String.class.getModifiers());
        doReturn(PUBLIC).when(visibilityService).toVisibility(packagePrivate.getModifiers());
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(type, packageName);
        // Assert
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    void testIsMethodAccessibleFromNull(final Method method, final String packageName) {
        // Act
        final ThrowingCallable isAccessibleFrom = () -> accessibilityService.isAccessibleFrom(method, packageName);
        // Assert
        assertThatThrownBy(isAccessibleFrom).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(visibilityService, typeService, javaClassService);
    }

    @SneakyThrows
    private static Stream<Arguments> testIsMethodAccessibleFromNull() {
        return Stream.of( //
                Arguments.of(null, null), //
                Arguments.of(Object.class.getDeclaredMethod("toString"), null), //
                Arguments.of(null, "a.package.name"));
    }

    @Test
    @SneakyThrows
    void testIsMethodAccessiblePrivateMethod() {
        // Arrange
        final Method method = SettersWithDifferentVisibility.class.getDeclaredMethod("setPrivateSetter", int.class);
        final String packageName = "does.not.matter";
        doReturn(PRIVATE).when(visibilityService).toVisibility(method.getModifiers());
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(method, packageName);
        // Assert
        assertThat(actual).isFalse();
    }

    @Test
    @SneakyThrows
    void testIsMethodAccessiblePublicMethodButInaccessibleReturnType() {
        // Arrange
        final Class<?> privateClass = Class.forName(PackagePrivateConstructor.class.getName() + "$PrivateClass");
        final Method method = PackagePrivateConstructor.class.getDeclaredMethod("getPrivateClass");
        final String packageName = "does.not.matter";
        doReturn(PRIVATE).when(visibilityService).toVisibility(privateClass.getModifiers());
        doReturn(PUBLIC).when(visibilityService).toVisibility(method.getModifiers());
        doReturn(singleton(privateClass)).when(typeService).explodeType(privateClass);
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(method, packageName);
        // Assert
        assertThat(actual).isFalse();
    }

    @Test
    @SneakyThrows
    void testIsMethodAccessiblePublicMethodButInaccessibleParameter() {
        // Arrange
        final Class<?> privateClass = Class.forName(PackagePrivateConstructor.class.getName() + "$PrivateClass");
        final Method method = PackagePrivateConstructor.class.getDeclaredMethod("setPrivateClass", privateClass);
        final String packageName = "does.not.matter";
        doReturn(PRIVATE).when(visibilityService).toVisibility(privateClass.getModifiers());
        doReturn(PUBLIC).when(visibilityService).toVisibility(method.getModifiers());
        doReturn(PUBLIC).when(visibilityService).toVisibility(void.class.getModifiers());
        doReturn(singleton(void.class)).when(typeService).explodeType(void.class);
        doReturn(singleton(privateClass)).when(typeService).explodeType(privateClass);
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(method, packageName);
        // Assert
        assertThat(actual).isFalse();
    }

    @Test
    @SneakyThrows
    void testIsMethodAccessible() {
        // Arrange
        final Method method = Object.class.getDeclaredMethod("toString");
        final String packageName = "does.not.matter";
        doReturn(PUBLIC).when(visibilityService).toVisibility(method.getModifiers());
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(method, packageName);
        // Assert
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    void testIsConstructorAccessibleFromNull(final Constructor<?> constructor, final String packageName) {
        // Act
        final ThrowingCallable isAccessibleFrom = () -> accessibilityService.isAccessibleFrom(constructor, packageName);
        // Assert
        assertThatThrownBy(isAccessibleFrom).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(visibilityService, typeService, javaClassService);
    }

    @SneakyThrows
    private static Stream<Arguments> testIsConstructorAccessibleFromNull() {
        return Stream.of( //
                Arguments.of(null, null), //
                Arguments.of(Object.class.getDeclaredConstructor(), null), //
                Arguments.of(null, "a.package.name"));
    }

    @ParameterizedTest
    @MethodSource
    @SneakyThrows
    void testIsConstructorAccessibleFrom(final Class<?> clazz, final String packageName, final Visibility visibility, final boolean expected) {
        // Arrange
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        doReturn(visibility).when(visibilityService).toVisibility(anyInt());
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(constructor, packageName);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testIsConstructorAccessibleFrom() {
        return testIsClassAccessibleFrom();
    }
}