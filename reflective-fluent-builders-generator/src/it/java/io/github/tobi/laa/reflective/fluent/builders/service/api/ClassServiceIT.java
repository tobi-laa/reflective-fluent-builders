package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.props.impl.StandardBuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.InjectSpy;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second.SecondSuperClassInDifferentPackage;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.NestedMarker;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.TopLevelClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Child;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Parent;
import lombok.SneakyThrows;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.invocation.InvocationOnMock;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@IntegrationTest
class ClassServiceIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private ClassService service;

    @InjectSpy
    private BuildersProperties properties;

    @Test
    void testCollectFullClassHierarchyNull() {
        // Act
        final Executable collectFullClassHierarchy = () -> service.collectFullClassHierarchy(null);
        // Assert
        assertThrows(NullPointerException.class, collectFullClassHierarchy);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectFullClassHierarchy(final ClassInfo clazz, final Set<Predicate<Class<?>>> excludes, final List<ClassInfo> expected) {
        // Arrange
        final var hierarchyCollection = new StandardBuildersProperties.StandardHierarchyCollection();
        hierarchyCollection.setExcludes(excludes);
        doReturn(hierarchyCollection).when(properties).getHierarchyCollection();
        // Act
        final List<ClassInfo> actual = service.collectFullClassHierarchy(clazz);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testCollectFullClassHierarchy() {
        return Stream.of( //
                Arguments.of( //
                        classInfo.get(ClassWithHierarchy.class.getName()), //
                        Collections.emptySet(), //
                        List.of( //
                                classInfo.get(ClassWithHierarchy.class.getName()), //
                                classInfo.get(AnInterface.class.getName()), //
                                classInfo.get(FirstSuperClass.class.getName()), //
                                classInfo.get(SecondSuperClassInDifferentPackage.class.getName()), //
                                classInfo.get(TopLevelSuperClass.class.getName()), //
                                classInfo.get(AnotherInterface.class.getName()))), //
                Arguments.of( //
                        classInfo.get(ClassWithHierarchy.class.getName()), //
                        Set.<Predicate<Class<?>>>of(Object.class::equals), //
                        List.of( //
                                classInfo.get(ClassWithHierarchy.class.getName()), //
                                classInfo.get(AnInterface.class.getName()), //
                                classInfo.get(FirstSuperClass.class.getName()), //
                                classInfo.get(SecondSuperClassInDifferentPackage.class.getName()), //
                                classInfo.get(TopLevelSuperClass.class.getName()), //
                                classInfo.get(AnotherInterface.class.getName()))), //
                Arguments.of( //
                        classInfo.get(ClassWithHierarchy.class.getName()), //
                        Set.<Predicate<Class<?>>>of(Object.class::equals, AnInterface.class::equals), //
                        List.of( //
                                classInfo.get(ClassWithHierarchy.class.getName()), //
                                classInfo.get(FirstSuperClass.class.getName()), //
                                classInfo.get(SecondSuperClassInDifferentPackage.class.getName()), //
                                classInfo.get(TopLevelSuperClass.class.getName()), //
                                classInfo.get(AnotherInterface.class.getName()))), //
                Arguments.of( //
                        classInfo.get(ClassWithHierarchy.class.getName()), //
                        Set.<Predicate<Class<?>>>of(FirstSuperClass.class::equals), //
                        List.of( //
                                classInfo.get(ClassWithHierarchy.class.getName()), //
                                classInfo.get(AnInterface.class.getName()))));
    }

    @Test
    void testCollectClassesRecursivelyNull() {
        // Arrange
        final String packageName = null;
        // Act
        final Executable collectClassesRecursively = () -> service.collectClassesRecursively(packageName);
        // Assert
        assertThrows(NullPointerException.class, collectClassesRecursively);
    }

    @SuppressWarnings({"unused", "resource"})
    @Test
    void testCollectClassesRecursivelyReflectionException() {
        // Arrange
        final var cause = classGraphException("Thrown in unit test");
        try (final var classGraph = mockConstruction(
                ClassGraph.class,
                withSettings().defaultAnswer(InvocationOnMock::getMock),
                (mock, ctx) -> doThrow(cause).when(mock).scan())) {
            // Act
            final ThrowingCallable collectClassesRecursively = () -> service.collectClassesRecursively("");
            // Assert
            assertThatThrownBy(collectClassesRecursively)
                    .isInstanceOf(ReflectionException.class)
                    .hasMessage("Error while attempting to collect classes recursively.")
                    .hasCause(cause);
        }
    }

    @SneakyThrows
    private ClassGraphException classGraphException(final String message) {
        final var constructor = ClassGraphException.class.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        return constructor.newInstance(message);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectClassesRecursively(final String packageName, final Set<ClassInfo> expected) {
        // Act
        final Set<ClassInfo> actual = service.collectClassesRecursively(packageName);
        // Assert
        assertThat(actual)
                .filteredOn(not(this::isTestClass))
                .map(ClassInfo::getName)
                .containsExactlyInAnyOrderElementsOf(expected.stream().map(ClassInfo::getName).collect(Collectors.toSet()));
    }

    private boolean isTestClass(final ClassInfo clazz) {
        return Arrays.stream(clazz.loadClass().getDeclaredMethods())
                .map(Method::getDeclaredAnnotations)
                .flatMap(Arrays::stream)
                .map(Annotation::annotationType)
                .anyMatch(Set.of(Test.class, ParameterizedTest.class)::contains);
    }

    @SneakyThrows
    private static Stream<Arguments> testCollectClassesRecursively() {
        return Stream.of( //
                Arguments.of(
                        Simple.class.getPackageName(), //
                        Set.of( //
                                classInfo.get(Child.class.getName()), //
                                classInfo.get(Parent.class.getName()), //
                                classInfo.get(Simple.class.getName()), //
                                classInfo.get(SimpleAbstractClass.class.getName()), //
                                classInfo.get(SimpleClass.class.getName()), //
                                classInfo.get(SimpleClassNoDefaultConstructor.class.getName()), //
                                classInfo.get(SimpleClassNoSetPrefix.class.getName()))),
                Arguments.of(
                        NestedMarker.class.getPackageName(), //
                        Set.of( //
                                classInfo.get(NestedMarker.class.getName()), //
                                classInfo.get(TopLevelClass.class.getName()))));
    }

    @Test
    void testDetermineClassLocationNull() {
        // Arrange
        final Class<?> clazz = null;
        // Act
        final Executable determineClassLocation = () -> service.determineClassLocation(clazz);
        // Assert
        assertThrows(NullPointerException.class, determineClassLocation);
    }

    @Test
    void testDetermineClassLocationCodeSourceNull() {
        // Arrange
        final var clazz = String.class;
        // Act
        final Optional<Path> actual = service.determineClassLocation(clazz);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    void testDetermineClassLocationFromJar() {
        // Arrange
        final var clazz = Test.class;
        // Act
        final Optional<Path> actual = service.determineClassLocation(clazz);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get()).isRegularFile().hasExtension("jar");
    }

    @Test
    void testDetermineClassLocationFromClassFile() {
        // Arrange
        final var clazz = getClass();
        // Act
        final Optional<Path> actual = service.determineClassLocation(clazz);
        // Assert
        assertThat(actual).isPresent();
        assertThat(actual.get()).isRegularFile().hasExtension("class");
    }

    @Test
    void testLoadClassNull() {
        // Arrange
        final String className = null;
        // Act
        final ThrowingCallable loadClass = () -> service.loadClass(className);
        // Assert
        assertThatThrownBy(loadClass).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    @SneakyThrows
    @SuppressWarnings({"unused", "resource"})
    void testLoadClassException() {
        // Arrange
        final var className = "does.not.matter";
        final var cause = classGraphException("Thrown in unit test.");
        try (final var classGraph = mockConstruction(
                ClassGraph.class,
                withSettings().defaultAnswer(InvocationOnMock::getMock),
                (mock, ctx) -> doThrow(cause).when(mock).scan())) {
            // Act
            final ThrowingCallable loadClass = () -> service.loadClass(className);
            // Assert
            assertThatThrownBy(loadClass) //
                    .isInstanceOf(ReflectionException.class)
                    .hasMessage("Error while attempting to load class does.not.matter.") //
                    .hasCause(cause);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"this.class.exists.not", "io.github.tobi.laa.reflective.fluent.builders.mojo.GenerateBuildersMojo"})
    void testLoadClassEmpty(final String className) {
        // Act
        final Optional<ClassInfo> actual = service.loadClass(className);
        // Assert
        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource
    void testLoadClass(final String className, final Class<?> expected) {
        // Act
        final Optional<ClassInfo> actual = service.loadClass(className);
        // Assert
        assertThat(actual).get().hasFieldOrPropertyWithValue("name", expected.getName());
    }

    private static Stream<Arguments> testLoadClass() {
        return Stream.of( //
                Arguments.of("java.lang.String", String.class), //
                Arguments.of("io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService", ClassService.class));
    }

    @Test
    void testIsAbstractNull() {
        // Arrange
        final Class<?> clazz = null;
        // Act
        final ThrowingCallable isAbstract = () -> service.isAbstract(clazz);
        // Assert
        assertThatThrownBy(isAbstract).isExactlyInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource
    void testIsAbstract(final Class<?> clazz, final boolean expected) {
        // Act
        final boolean actual = service.isAbstract(clazz);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testIsAbstract() {
        return Stream.of( //
                Arguments.of(SimpleClass.class, false), //
                Arguments.of(SimpleAbstractClass.class, true));
    }
}