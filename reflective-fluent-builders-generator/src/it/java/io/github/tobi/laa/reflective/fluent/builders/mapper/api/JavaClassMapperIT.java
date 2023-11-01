package io.github.tobi.laa.reflective.fluent.builders.mapper.api;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import io.github.tobi.laa.reflective.fluent.builders.Marker;
import io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaClass;
import io.github.tobi.laa.reflective.fluent.builders.sisu.SisuExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second.SecondSuperClassInDifferentPackage;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.platform.commons.JUnitException;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.PUBLIC;
import static io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaType.CLASS;
import static io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaType.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SisuExtension.class)
class JavaClassMapperIT {

    @Inject
    private JavaClassMapper mapper;

    private ClassInfo givenClassInfo;

    private ThrowingCallable map;

    private JavaClass actualJavaClass;

    @BeforeEach
    void resetTestData() {
        givenClassInfo = null;
        map = null;
        actualJavaClass = null;
    }

    @Test
    void classInfoNull_whenCallingMap_shouldThrowException() {
        givenClassInfo(null);
        whenCallingMap();
        thenNullPointerExceptionShouldBeThrown();
    }

    @ParameterizedTest
    @ArgumentsSource(ArgsProvider.class)
    void validClassInfo_whenCallingMap_shouldReturnExpectedJavaClass(final ClassInfo classInfo, final JavaClass expected) {
        givenClassInfo(classInfo);
        whenCallingMap();
        thenReturnedJavaClassShouldBe(expected);
    }

    private void givenClassInfo(final ClassInfo classInfo) {
        givenClassInfo = classInfo;
    }

    private void whenCallingMap() {
        map = () -> actualJavaClass = mapper.map(givenClassInfo);
    }

    private void thenNullPointerExceptionShouldBeThrown() {
        assertThatThrownBy(map).isExactlyInstanceOf(NullPointerException.class);
    }

    private void thenReturnedJavaClassShouldBe(final JavaClass expected) {
        assertThatCode(map).doesNotThrowAnyException();
        assertThat(actualJavaClass)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withEqualsForType((s1, s2) -> s1.get().equals(s2.get()), Supplier.class)
                        .withIgnoredFields("classWrapper")
                        .build())
                .isEqualTo(expected);
    }

    static class ArgsProvider implements ArgumentsProvider {

        private final ClassInfoList classes;

        ArgsProvider() {
            try (final ScanResult scanResult = new ClassGraph()
                    .enableAllInfo()
                    .enableSystemJarsAndModules()
                    .acceptPackages(Marker.class.getPackageName())
                    .acceptClasses(
                            String.class.getName(),
                            List.class.getName())
                    .scan()) {
                classes = scanResult.getAllClasses();
            }
        }

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext extensionContext) {
            final JavaClass serializable = javaClass(Serializable.class).type(INTERFACE).build();
            final JavaClass comparable = javaClass(Comparable.class).type(INTERFACE).build();
            final JavaClass charSequence = javaClass(CharSequence.class).type(INTERFACE).build();
            final JavaClass iterable = javaClass(Iterable.class).type(INTERFACE).build();
            final JavaClass collection = javaClass(Collection.class).type(INTERFACE).addInterface(iterable).build();
            final JavaClass anInterface = javaClass(AnInterface.class)
                    .type(INTERFACE)
                    .classLocation(loc(AnInterface.class))
                    .sourceLocation(src(AnInterface.class))
                    .build();
            final JavaClass anotherInterface = javaClass(AnotherInterface.class)
                    .type(INTERFACE)
                    .classLocation(loc(AnotherInterface.class))
                    .sourceLocation(src(AnotherInterface.class))
                    .build();
            return Stream.of(
                    Arguments.of(
                            classInfo(String.class),
                            javaClass(String.class)
                                    .outerClass(true)
                                    .addInterface(serializable)
                                    .addInterface(comparable)
                                    .addInterface(charSequence)
                                    .build()),
                    Arguments.of(
                            classInfo(List.class),
                            javaClass(List.class)
                                    .type(INTERFACE)
                                    .addInterface(collection)
                                    .addInterface(iterable)
                                    .build()),
                    Arguments.of(
                            classInfo(ClassWithHierarchy.class),
                            javaClass(ClassWithHierarchy.class)
                                    .classLocation(loc(ClassWithHierarchy.class))
                                    .sourceLocation(src(ClassWithHierarchy.class))
                                    .superclass(javaClass(FirstSuperClass.class)
                                            .classLocation(loc(FirstSuperClass.class))
                                            .sourceLocation(src(FirstSuperClass.class))
                                            .addInterface(anotherInterface)
                                            .superclass(javaClass(SecondSuperClassInDifferentPackage.class)
                                                    .classLocation(loc(SecondSuperClassInDifferentPackage.class))
                                                    .sourceLocation(src(SecondSuperClassInDifferentPackage.class))
                                                    .addInterface(anotherInterface)
                                                    .superclass(javaClass(TopLevelSuperClass.class)
                                                            .type(ABSTRACT_CLASS)
                                                            .classLocation(loc(TopLevelSuperClass.class))
                                                            .sourceLocation(src(TopLevelSuperClass.class))
                                                            .addInterface(anotherInterface)
                                                            .build())
                                                    .build())
                                            .build())
                                    .addInterface(anInterface)
                                    .addInterface(anotherInterface)
                                    .classLocation(loc(ClassWithHierarchy.class))
                                    .sourceLocation(src(ClassWithHierarchy.class))
                                    .build())
            );
        }

        private ClassInfo classInfo(final Class<?> clazz) {
            return classes
                    .stream()
                    .filter(classInfo -> clazz.getName().equals(classInfo.getName()))
                    .findFirst()
                    .orElseThrow(() -> new JUnitException("ClassInfo not found: " + clazz));
        }

        private JavaClass.JavaClassBuilder javaClass(final Class<?> clazz) {
            return JavaClass.builder()
                    .name(clazz.getName())
                    .simpleName(clazz.getSimpleName())
                    .packageName(clazz.getPackage().getName())
                    .classSupplier(() -> clazz)
                    .isStatic(Modifier.isStatic(clazz.getModifiers()))
                    .innerClass(clazz.isMemberClass() || clazz.isAnonymousClass())
                    .visibility(PUBLIC)
                    .type(CLASS);
        }

        private Path loc(final Class<?> clazz) {
            Path classLocation = Paths.get("")
                    .toAbsolutePath()
                    .getParent()
                    .resolve("reflective-fluent-builders-test-models")
                    .resolve("target")
                    .resolve("classes");
            for (final String pack : clazz.getPackageName().split("\\.")) {
                classLocation = classLocation.resolve(pack);
            }
            return classLocation.resolve(clazz.getSimpleName() + ".class");
        }

        private Path src(final Class<?> clazz) {
            return Paths.get(clazz.getSimpleName() + ".java");
        }
    }
}
