package io.github.tobi.laa.reflective.fluent.builders.service.api;


import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IntegrationTest
class AccessibilityServiceIT {

    @Inject
    private AccessibilityService accessibilityService;

    @ParameterizedTest
    @MethodSource
    void testIsClassAccessibleFromNull(final Class<?> clazz, final String packageName) {
        // Act
        final ThrowingCallable isAccessibleFrom = () -> accessibilityService.isAccessibleFrom(clazz, packageName);
        // Assert
        assertThatThrownBy(isAccessibleFrom).isExactlyInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> testIsClassAccessibleFromNull() {
        return Stream.of( //
                Arguments.of(null, null), //
                Arguments.of(String.class, null), //
                Arguments.of(null, "a.package.name"));
    }

    @ParameterizedTest
    @MethodSource
    void testIsClassAccessibleFrom(final Class<?> clazz, final String packageName, final boolean expected) {
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(clazz, packageName);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @SneakyThrows
    private static Stream<Arguments> testIsClassAccessibleFrom() {
        final String packageName = Visibility.class.getPackage().getName();
        final Class<?> privateClass = Class.forName(InnerPrivateClass.class.getName() + "$PrivateInnerClass");
        final Class<?> packagePrivateClass = Class.forName(packageName + ".PackagePrivate");
        final Class<?> packagePrivateAbstractClass = Class.forName(packageName + ".PackagePrivateAbstract");
        return Stream.of( //
                Arguments.of(privateClass, packageName, false), //
                Arguments.of(packagePrivateClass, packageName, true), //
                Arguments.of(packagePrivateAbstractClass, packageName, false), //
                Arguments.of(Public.class, packageName, true), //
                //
                Arguments.of(privateClass, "a.weird.package", false), //
                Arguments.of(packagePrivateClass, "a.weird.package", false), //
                Arguments.of(packagePrivateAbstractClass, "a.weird.package", false), //
                Arguments.of(Public.class, "a.weird.package", true));
    }

    @ParameterizedTest
    @MethodSource
    void testIsTypeAccessibleFromNull(final Type type, final String packageName) {
        // Act
        final ThrowingCallable isAccessibleFrom = () -> accessibilityService.isAccessibleFrom(type, packageName);
        // Assert
        assertThatThrownBy(isAccessibleFrom).isExactlyInstanceOf(NullPointerException.class);
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
        final Class<?> privateClass = Class.forName(InnerPrivateClass.class.getName() + "$PrivateInnerClass");
        final Type type = TypeUtils.parameterize(List.class, privateClass);
        final String packageName = Visibility.class.getPackage().getName();
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(type, packageName);
        // Assert
        assertThat(actual).isFalse();
    }

    @Test
    @SneakyThrows
    void testIsTypeAccessibleFrom() {
        // Arrange
        final Type type = TypeUtils.parameterize(List.class, String.class);
        final String packageName = Visibility.class.getPackage().getName();
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
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(method, packageName);
        // Assert
        assertThat(actual).isFalse();
    }

    @Test
    @SneakyThrows
    void testIsMethodAccessiblePublicMethodButInaccessibleReturnType() {
        // Arrange
        final Method method = PackagePrivateConstructor.class.getDeclaredMethod("getPrivateClass");
        final String packageName = "does.not.matter";
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
    void testIsConstructorAccessibleFrom(final Class<?> clazz, final String packageName, final boolean expected) {
        // Arrange
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        // Act
        final boolean actual = accessibilityService.isAccessibleFrom(constructor, packageName);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testIsConstructorAccessibleFrom() {
        return testIsClassAccessibleFrom();
    }
}