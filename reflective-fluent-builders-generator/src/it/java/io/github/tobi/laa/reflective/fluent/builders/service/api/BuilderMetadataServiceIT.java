package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.TopLevelClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoDefaultConstructor;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Abstract;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Annotation;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Enum;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Interface;
import io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivateConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.PACKAGE_PRIVATE;
import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.PUBLIC;
import static java.util.Collections.singleton;
import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.apache.commons.lang3.reflect.TypeUtils.wildcardType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

@IntegrationTest
class BuilderMetadataServiceIT {

    @Inject
    private BuilderMetadataService service;

    @SpyBean
    private BuildersProperties properties;

    @Test
    void testCollectBuilderMetadataNull() {
        // Act
        final Executable collectBuilderMetadata = () -> service.collectBuilderMetadata(null);
        // Assert
        assertThrows(NullPointerException.class, collectBuilderMetadata);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectBuilderMetadata(final String builderPackage, final String builderSuffix, final String setterPrefix, final Class<?> clazz, final BuilderMetadata expected) {
        // Arrange
        doReturn(builderPackage).when(properties).getBuilderPackage();
        doReturn(builderSuffix).when(properties).getBuilderSuffix();
        doReturn(setterPrefix).when(properties).getSetterPrefix();
        // Act
        final BuilderMetadata actual = service.collectBuilderMetadata(clazz);
        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .withEqualsForType(
                        (a, b) -> ((Type) a).getTypeName().equals(((Type) b).getTypeName()),
                        Type.class)
                .isEqualTo(expected);
    }

    @SneakyThrows
    private static Stream<Arguments> testCollectBuilderMetadata() {
        final var packagePrivate = Class.forName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivate");
        final var setOfList = ClassWithCollections.class.getDeclaredField("set").getGenericType();
        return Stream.of( //
                Arguments.of( //
                        "io.github.tobi.laa.reflective.fluent.builders.test.models.simple", //
                        "Builder", //
                        "set", //
                        SimpleClass.class, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
                                        .location(classLocation(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(SimpleSetter.builder()
                                                .methodName("setAnInt")
                                                .paramName("anInt")
                                                .paramType(int.class)
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .setter(SimpleSetter.builder()
                                                .methodName("setAString")
                                                .paramName("aString")
                                                .paramType(String.class)
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .setter(SimpleSetter.builder()
                                                .methodName("setBooleanField")
                                                .paramName("booleanField")
                                                .paramType(boolean.class)
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .setter(SimpleSetter.builder()
                                                .methodName("setSetClass")
                                                .paramName("setClass")
                                                .paramType(parameterize(Class.class, wildcardType().build()))
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "io.github.tobi.laa.reflective.fluent.builders.test.models.complex.builder", //
                        "", //
                        "set", //
                        ClassWithCollections.class, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.complex.builder") //
                                .name("ClassWithCollections") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(ClassWithCollections.class) //
                                        .location(classLocation(ClassWithCollections.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(CollectionSetter.builder().methodName("setInts").paramName("ints").paramType(parameterize(Collection.class, Integer.class)).paramTypeArg(Integer.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(CollectionSetter.builder().methodName("setList").paramName("list").paramType(List.class).paramTypeArg(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(CollectionSetter.builder().methodName("setSet").paramName("set").paramType(setOfList).paramTypeArg(List.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(CollectionSetter.builder().methodName("setDeque").paramName("deque").paramType(parameterize(Deque.class, wildcardType().withUpperBounds(Object.class).build())).paramTypeArg(wildcardType().withUpperBounds(Object.class).build()).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(CollectionSetter.builder().methodName("setSortedSetWild").paramName("sortedSetWild").paramType(parameterize(SortedSet.class, wildcardType().build())).paramTypeArg(wildcardType().build()).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(ArraySetter.builder().methodName("setFloats").paramName("floats").paramType(float[].class).paramComponentType(float.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(MapSetter.builder().methodName("setMap").paramName("map").paramType(parameterize(Map.class, String.class, Object.class)).keyType(String.class).valueType(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(MapSetter.builder().methodName("setMapTU").paramName("mapTU").paramType(parameterize(Map.class, typeVariableT(), typeVariableU())).keyType(typeVariableT()).valueType(typeVariableU()).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(MapSetter.builder().methodName("setMapWildObj").paramName("mapWildObj").paramType(parameterize(Map.class, wildcardType().build(), Object.class)).keyType(wildcardType().build()).valueType(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(MapSetter.builder().methodName("setMapNoTypeArgs").paramName("mapNoTypeArgs").paramType(Map.class).keyType(Object.class).valueType(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(CollectionSetter.builder().methodName("setListWithTwoParams").paramName("listWithTwoParams").paramType(parameterize(ListWithTwoParams.class, String.class, Integer.class)).paramTypeArg(parameterize(Map.class, String.class, Integer.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .setter(MapSetter.builder().methodName("setMapWithThreeParams").paramName("mapWithThreeParams").paramType(parameterize(MapWithThreeParams.class, String.class, Integer.class, Boolean.class)).keyType(String.class).valueType(parameterize(Map.class, Integer.class, Boolean.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "io.github.tobi.laa.reflective.fluent.builders.test.models.visibility", //
                        "", //
                        "set", //
                        PackagePrivateConstructor.class, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility") //
                                .name("PackagePrivateConstructor0") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(PackagePrivateConstructor.class) //
                                        .location(classLocation(PackagePrivateConstructor.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(SimpleSetter.builder() //
                                                .methodName("setIntField") //
                                                .paramName("intField") //
                                                .paramType(int.class) //
                                                .visibility(PUBLIC) //
                                                .declaringClass(PackagePrivateConstructor.class) //
                                                .build()) //
                                        .setter(SimpleSetter.builder() //
                                                .methodName("setPackagePrivate") //
                                                .paramName("packagePrivate") //
                                                .paramType(packagePrivate) //
                                                .visibility(PUBLIC) //
                                                .declaringClass(PackagePrivateConstructor.class) //
                                                .build()) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        BuilderConstants.PACKAGE_PLACEHOLDER, //
                        "Builder", //
                        "set", //
                        SimpleClassNoSetPrefix.class, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoSetPrefixBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClassNoSetPrefix.class) //
                                        .location(classLocation(SimpleClassNoSetPrefix.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        BuilderConstants.PACKAGE_PLACEHOLDER, //
                        "MyBuilderSuffix", //
                        "", //
                        SimpleClassNoSetPrefix.class, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoSetPrefixMyBuilderSuffix") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClassNoSetPrefix.class) //
                                        .location(classLocation(SimpleClassNoSetPrefix.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(SimpleSetter.builder() //
                                                .methodName("anInt") //
                                                .paramName("anInt") //
                                                .paramType(int.class) //
                                                .visibility(PACKAGE_PRIVATE) //
                                                .declaringClass(SimpleClassNoSetPrefix.class) //
                                                .build()) //
                                        .setter(SimpleSetter.builder() //
                                                .methodName("aString") //
                                                .paramName("aString") //
                                                .paramType(String.class) //
                                                .visibility(PACKAGE_PRIVATE) //
                                                .declaringClass(SimpleClassNoSetPrefix.class) //
                                                .build()) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "builders.io.github.tobi.laa.reflective.fluent.builders.test.models.simple", //
                        "Builder", //
                        "set", //
                        SimpleClassNoDefaultConstructor.class, //
                        BuilderMetadata.builder() //
                                .packageName("builders.io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoDefaultConstructorBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClassNoDefaultConstructor.class) //
                                        .location(classLocation(SimpleClassNoDefaultConstructor.class)) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build()) //
        );
    }

    private static Path classLocation(final Class<?> clazz) {
        Path classLocation = Paths.get("").toAbsolutePath()
                .getParent()
                .resolve("reflective-fluent-builders-test-models")
                .resolve("target")
                .resolve("classes");
        for (final String dir : clazz.getPackageName().split("\\.")) {
            classLocation = classLocation.resolve(dir);
        }
        return classLocation.resolve(clazz.getSimpleName() + ".class");
    }

    @Test
    void testFilterOutNonBuildableClassesNull() {
        // Act
        final Executable filterOutNonBuildableClasses = () -> service.filterOutNonBuildableClasses(null);
        // Assert
        assertThrows(NullPointerException.class, filterOutNonBuildableClasses);
    }

    @Test
    void testFilterOutNonBuildableClassesNonConstructableClasses() {
        // Arrange
        final var classes = Set.of(Abstract.class, Annotation.class, Enum.class, Interface.class);
        // Act
        final Set<Class<?>> actual = service.filterOutNonBuildableClasses(classes);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    @SneakyThrows
    void testFilterOutNonBuildableClassesInaccessibleClass() {
        // Arrange
        final var classes = Set.of(
                SimpleClass.class,
                Class.forName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivate"));
        doReturn("a.package").when(properties).getBuilderPackage();
        // Act
        final Set<Class<?>> actual = service.filterOutNonBuildableClasses(classes);
        // Assert
        assertThat(actual).containsExactly(SimpleClass.class);
    }

    @Test
    void testFilterOutNonBuildableClassesNestedClasses() {
        // Arrange
        final var classes = Set.of(TopLevelClass.NestedPublicLevelOne.class, TopLevelClass.NestedNonStatic.class);
        // Act
        final Set<Class<?>> actual = service.filterOutNonBuildableClasses(classes);
        // Assert
        assertThat(actual).containsExactly(TopLevelClass.NestedPublicLevelOne.class);
    }

    @Test
    void testFilterOutConfiguredExcludesNull() {
        // Act
        final Executable filterOutConfiguredExcludes = () -> service.filterOutConfiguredExcludes(null);
        // Assert
        assertThrows(NullPointerException.class, filterOutConfiguredExcludes);
    }

    @ParameterizedTest
    @MethodSource
    void testFilterOutConfiguredExcludes(final Set<Predicate<Class<?>>> excludes, final Set<Class<?>> classes, final Set<Class<?>> expected) {
        // Arrange
        lenient().when(properties.getExcludes()).thenReturn(excludes);
        // Act
        final Set<Class<?>> actual = service.filterOutConfiguredExcludes(classes);
        // Assert
        assertEquals(expected, actual);
    }

    @SneakyThrows
    private static Stream<Arguments> testFilterOutConfiguredExcludes() {
        return Stream.of( //
                Arguments.of( //
                        Collections.emptySet(), //
                        Collections.emptySet(), //
                        Collections.emptySet()), //
                Arguments.of( //
                        Collections.emptySet(), //
                        Set.of(SimpleClass.class), //
                        Set.of(SimpleClass.class)), //
                Arguments.of( //
                        Set.<Predicate<Class<?>>>of(cl -> cl.getPackageName().equals(Complex.class.getPackageName())), //
                        Set.of(SimpleClass.class, ClassWithCollections.class, ClassWithGenerics.class), //
                        Set.of(SimpleClass.class)), //
                Arguments.of( //
                        Set.<Predicate<Class<?>>>of(SimpleClass.class::equals), //
                        Set.of(SimpleClass.class, ClassWithCollections.class, ClassWithGenerics.class), //
                        Set.of(ClassWithCollections.class, ClassWithGenerics.class)), //
                Arguments.of( //
                        Set.<Predicate<Class<?>>>of(cl -> cl.getName().endsWith("Generics")), //
                        Set.of(SimpleClass.class, ClassWithCollections.class, ClassWithGenerics.class), //
                        Set.of(SimpleClass.class, ClassWithCollections.class)));
    }

    @Test
    void testFilterOutEmptyBuildersNull() {
        // Arrange
        final Collection<BuilderMetadata> builderMetadata = null;
        // Act
        final Executable filterOutEmptyBuilders = () -> service.filterOutEmptyBuilders(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, filterOutEmptyBuilders);
    }

    @ParameterizedTest
    @MethodSource
    void testFilterOutEmptyBuilders(final Collection<BuilderMetadata> builderMetadata, final Set<BuilderMetadata> expected) {
        // Act
        final Set<BuilderMetadata> actual = service.filterOutEmptyBuilders(builderMetadata);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testFilterOutEmptyBuilders() {
        return Stream.of( //
                Arguments.of(Collections.emptySet(), Collections.emptySet()), //
                Arguments.of(
                        singleton( //
                                BuilderMetadata.builder() //
                                        .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(SimpleClass.class) //
                                                .accessibleNonArgsConstructor(true) //
                                                .build()) //
                                        .build()),
                        Collections.emptySet()),
                Arguments.of(
                        Set.of( //
                                BuilderMetadata.builder() //
                                        .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(SimpleClass.class) //
                                                .accessibleNonArgsConstructor(true) //
                                                .build()) //
                                        .build(),
                                BuilderMetadata.builder() //
                                        .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(SimpleClass.class) //
                                                .accessibleNonArgsConstructor(true) //
                                                .setter(SimpleSetter.builder() //
                                                        .methodName("setPub") //
                                                        .paramName("pub") //
                                                        .paramType(int.class) //
                                                        .visibility(PUBLIC) //
                                                        .declaringClass(SimpleClass.class) //
                                                        .build())
                                                .build()) //
                                        .build()),
                        singleton( //
                                BuilderMetadata.builder() //
                                        .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(SimpleClass.class) //
                                                .accessibleNonArgsConstructor(true) //
                                                .setter(SimpleSetter.builder() //
                                                        .methodName("setPub") //
                                                        .paramName("pub") //
                                                        .paramType(int.class) //
                                                        .visibility(PUBLIC) //
                                                        .declaringClass(SimpleClass.class) //
                                                        .build())
                                                .build()) //
                                        .build())));
    }

    @Test
    void testFilterOutConfiguredExcludesWithDefaultConfig() {
        // Act
        final var filteredClasses = service.filterOutConfiguredExcludes(Set.of( //
                SimpleClass.class, //
                ClassWithBuilderExisting.class, //
                ClassWithBuilderExisting.ClassWithBuilderExistingBuilder.class, //
                HasTheSuffixBuilderImpl.class));
        // Assert
        assertThat(filteredClasses).contains(SimpleClass.class, ClassWithBuilderExisting.class);
    }

    private static TypeVariable<?> typeVariableT() {
        return ClassWithCollections.class.getTypeParameters()[0];
    }

    private static TypeVariable<?> typeVariableU() {
        return ClassWithCollections.class.getTypeParameters()[1];
    }

    @SuppressWarnings("unused")
    private static class ClassWithMarkerField {

        private boolean ______generatedByReflectiveFluentBuildersGenerator;
    }

    @SuppressWarnings("unused")
    private static class ClassWithoutMarkerField {
        // no content
    }

    private static class HasTheSuffixBuilderImpl {
        // no content
    }
}
