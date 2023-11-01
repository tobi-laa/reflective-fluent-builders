package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import io.github.tobi.laa.reflective.fluent.builders.mapper.api.JavaClassMapper;
import io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaClass;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.JavaClassService;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second.SecondSuperClassInDifferentPackage;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.NestedMarker;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.TopLevelClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Child;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Parent;
import lombok.SneakyThrows;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.PUBLIC;
import static io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaType.*;
import static java.lang.ClassLoader.getSystemClassLoader;
import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JavaClassServiceImplTest {

    private JavaClassServiceImpl classServiceImpl;

    @Mock
    private JavaClassMapper mapper;

    @Mock
    private BuildersProperties properties;

    @BeforeEach
    void init() {
        classServiceImpl = new JavaClassServiceImpl(mapper, properties, ClassLoader::getSystemClassLoader);
    }

    @Test
    void testCollectFullClassHierarchyNull() {
        // Act
        final Executable collectFullClassHierarchy = () -> classServiceImpl.collectFullClassHierarchy(null);
        // Assert
        assertThrows(NullPointerException.class, collectFullClassHierarchy);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectFullClassHierarchy(final JavaClass clazz, final Set<Predicate<JavaClass>> excludes, final List<Class<?>> expected) {
        // Arrange
        final var hierarchyCollection = Mockito.mock(BuildersProperties.HierarchyCollection.class);
        when(properties.getHierarchyCollection()).thenReturn(hierarchyCollection);
        when(hierarchyCollection.getExcludes()).thenReturn(excludes);
        // Act
        final List<JavaClass> actual = classServiceImpl.collectFullClassHierarchy(clazz);
        // Assert
        assertThat(actual).map(JavaClass::loadClass).isEqualTo(expected);
    }

    private static Stream<Arguments> testCollectFullClassHierarchy() {
        final var javaClass = JavaClass.builder()
                .classSupplier(() -> ClassWithHierarchy.class)
                .visibility(PUBLIC)
                .type(CLASS)
                .superclass(JavaClass.builder()
                        .classSupplier(() -> FirstSuperClass.class)
                        .visibility(PUBLIC)
                        .type(CLASS)
                        .superclass(JavaClass.builder()
                                .classSupplier(() -> SecondSuperClassInDifferentPackage.class)
                                .visibility(PUBLIC)
                                .superclass(JavaClass.builder()
                                        .classSupplier(() -> TopLevelSuperClass.class)
                                        .visibility(PUBLIC)
                                        .type(ABSTRACT_CLASS)
                                        .superclass(JavaClass.builder()
                                                .classSupplier(() -> Object.class)
                                                .type(CLASS)
                                                .visibility(PUBLIC)
                                                .build())
                                        .addInterface(JavaClass.builder()
                                                .classSupplier(() -> AnotherInterface.class)
                                                .type(INTERFACE)
                                                .visibility(PUBLIC)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .addInterface(JavaClass.builder()
                        .classSupplier(() -> AnInterface.class)
                        .type(INTERFACE)
                        .visibility(PUBLIC)
                        .build())
                .build();
        return Stream.of( //
                Arguments.of( //
                        javaClass, //
                        Collections.emptySet(), //
                        List.of( //
                                ClassWithHierarchy.class, //
                                AnInterface.class, //
                                FirstSuperClass.class, //
                                SecondSuperClassInDifferentPackage.class, //
                                TopLevelSuperClass.class, //
                                AnotherInterface.class, //
                                Object.class)), //
                Arguments.of( //
                        javaClass, //
                        Set.<Predicate<JavaClass>>of(clazz -> clazz.getName().equals(Object.class.getName())), //
                        List.of( //
                                ClassWithHierarchy.class, //
                                AnInterface.class, //
                                FirstSuperClass.class, //
                                SecondSuperClassInDifferentPackage.class, //
                                TopLevelSuperClass.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        javaClass, //
                        Set.<Predicate<JavaClass>>of(
                                clazz -> clazz.getName().equals(Object.class.getName()),
                                clazz -> clazz.getName().equals(AnInterface.class.getName())), //
                        List.of( //
                                ClassWithHierarchy.class, //
                                FirstSuperClass.class, //
                                SecondSuperClassInDifferentPackage.class, //
                                TopLevelSuperClass.class, //
                                AnotherInterface.class)), //
                Arguments.of( //
                        javaClass, //
                        Set.<Predicate<JavaClass>>of(clazz -> clazz.getName().equals(FirstSuperClass.class.getName())), //
                        List.of( //
                                ClassWithHierarchy.class, //
                                AnInterface.class)));
    }

    private static JavaClass asJavaClass(final Class<?> clazz) {
        return JavaClass.builder().classSupplier(() -> clazz).type(CLASS).visibility(PUBLIC).build();
    }

    @Test
    void testCollectClassesRecursivelyNull() {
        // Arrange
        final String packageName = null;
        // Act
        final Executable collectClassesRecursively = () -> classServiceImpl.collectClassesRecursively(packageName);
        // Assert
        assertThrows(NullPointerException.class, collectClassesRecursively);
    }

    @SuppressWarnings("unused")
    @Test
    void testCollectClassesRecursivelyReflectionException() {
        // Arrange
        final var cause = classGraphException("Thrown in unit test");
        try (final var classGraph = mockConstruction(
                ClassGraph.class,
                withSettings().defaultAnswer(InvocationOnMock::getMock),
                (mock, ctx) -> {
                    doThrow(cause).when(mock).scan();
                })) {
            // Act
            final ThrowingCallable collectClassesRecursively = () -> classServiceImpl.collectClassesRecursively("");
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
    void testCollectClassesRecursively(final String packageName, final Set<Class<?>> expected) {
        // Arrange
        doAnswer(invocation -> {
            final ClassInfo classInfo = (ClassInfo) invocation.getArguments()[0];
            return asJavaClass(classInfo.loadClass());
        }).when(mapper).map(any(ClassInfo.class));
        // Act
        final Set<JavaClass> actual = classServiceImpl.collectClassesRecursively(packageName);
        // Assert
        assertThat(actual)
                .filteredOn(not(this::isTestClass))
                .<Class<?>>map(JavaClass::loadClass)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private boolean isTestClass(final JavaClass clazz) {
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
                                Child.class, //
                                Parent.class, //
                                Simple.class, //
                                SimpleAbstractClass.class, //
                                SimpleClass.class, //
                                SimpleClassNoDefaultConstructor.class, //
                                SimpleClassNoSetPrefix.class)),
                Arguments.of(
                        NestedMarker.class.getPackageName(), //
                        Set.of( //
                                NestedMarker.class, //
                                TopLevelClass.class, //
                                TopLevelClass.NestedPublicLevelOne.class, //
                                Class.forName(TopLevelClass.class.getName() + "$NestedProtectedLevelOne"), //
                                Class.forName(TopLevelClass.class.getName() + "$NestedPackagePrivateLevelOne"), //
                                Class.forName(TopLevelClass.class.getName() + "$NestedPrivateLevelOne"), //
                                TopLevelClass.NestedNonStatic.class, //
                                TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.class, //
                                TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree.class)));
    }

    @Test
    @SneakyThrows
    void testGetLocationAsPathURISyntaxException() {
        // Arrange
        final var codeSource = Mockito.mock(CodeSource.class);
        final var url = Mockito.mock(URL.class);
        when(codeSource.getLocation()).thenReturn(url);
        when(url.toURI()).thenThrow(new URISyntaxException("mock", "Thrown in unit test."));
        // Act
        final Executable getLocationAsPath = () -> classServiceImpl.getLocationAsPath(codeSource);
        // Assert
        assertThrows(URISyntaxException.class, getLocationAsPath);
    }

    @Test
    void testLoadClassNull() {
        // Arrange
        final String className = null;
        final var classLoader = spy(getSystemClassLoader());
        classServiceImpl = new JavaClassServiceImpl(mapper, properties, () -> classLoader);
        // Act
        final ThrowingCallable loadClass = () -> classServiceImpl.loadClass(className);
        // Assert
        assertThatThrownBy(loadClass).isExactlyInstanceOf(NullPointerException.class);
        verifyNoInteractions(classLoader);
    }

    @Test
    @SneakyThrows
    void testLoadClassException() {
        // Arrange
        final var cause = classGraphException("Thrown in unit test");
        final var className = "does.not.matter";
        try (final var classGraph = mockConstruction(
                ClassGraph.class,
                withSettings().defaultAnswer(InvocationOnMock::getMock),
                (mock, ctx) -> {
                    doThrow(cause).when(mock).scan();
                })) {
            // Act
            final ThrowingCallable loadClass = () -> classServiceImpl.loadClass(className);
            // Assert
            assertThatThrownBy(loadClass) //
                    .isExactlyInstanceOf(ReflectionException.class) //
                    .hasMessage("Error while attempting to load class does.not.matter.") //
                    .hasCause(cause);
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"this.class.exists.not", "io.github.tobi.laa.reflective.fluent.builders.mojo.GenerateBuildersMojo"})
    void testLoadClassEmpty(final String className) {
        // Act
        final Optional<JavaClass> actual = classServiceImpl.loadClass(className);
        // Assert
        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource
    void testLoadClass(final String className, final Class<?> expected) {
        // Arrange
        doReturn(asJavaClass(expected)).when(mapper).map(any(ClassInfo.class));
        // Act
        final Optional<JavaClass> actual = classServiceImpl.loadClass(className);
        // Assert
        assertThat(actual).map(JavaClass::loadClass).get().isEqualTo(expected);
    }

    private static Stream<Arguments> testLoadClass() {
        return Stream.of( //
                Arguments.of("java.lang.String", String.class), //
                Arguments.of("io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService", JavaClassService.class));
    }
}