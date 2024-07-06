package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.InjectSpy;
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
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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

    private static final Path TEST_MODELS_TARGET_DIR = Paths.get("").toAbsolutePath()
            .getParent()
            .resolve("reflective-fluent-builders-test")
            .resolve("target");

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private BuilderMetadataService service;

    @InjectSpy
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
    void testCollectBuilderMetadata(final String builderPackage, final String builderSuffix, final String setterPrefix, final ClassInfo clazz, final BuilderMetadata expected) {
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
                        (a, b) -> a.getTypeName().equals(b.getTypeName()),
                        Type.class)
                .ignoringFields("builtType.location")
                .isEqualTo(expected);
        assertThat(actual.getBuiltType().getLocation()).get().satisfiesAnyOf(
                location -> assertThat(Optional.of(location)).isEqualTo(expected.getBuiltType().getLocation()),
                location -> assertThat(location)
                        .hasParent(TEST_MODELS_TARGET_DIR)
                        .asString().matches(".+/reflective-fluent-builders-test-.+.jar"));
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
                        classInfo.get(SimpleClass.class.getName()), //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class.getName())) //
                                        .location(classLocation(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .writeAccessor(Setter.builder()
                                                .methodName("setAnInt")
                                                .propertyName("anInt")
                                                .propertyType(new SimpleType(int.class))
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .writeAccessor(Setter.builder()
                                                .methodName("setAString")
                                                .propertyName("aString")
                                                .propertyType(new SimpleType(String.class))
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .writeAccessor(Setter.builder()
                                                .methodName("setBooleanField")
                                                .propertyName("booleanField")
                                                .propertyType(new SimpleType(boolean.class))
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .writeAccessor(Setter.builder()
                                                .methodName("setSetClass")
                                                .propertyName("setClass")
                                                .propertyType(new SimpleType(parameterize(Class.class, wildcardType().build())))
                                                .visibility(PUBLIC)
                                                .declaringClass(SimpleClass.class)
                                                .build())
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "io.github.tobi.laa.reflective.fluent.builders.test.models.complex.builder", //
                        "", //
                        "set", //
                        classInfo.get(ClassWithCollections.class.getName()), //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.complex.builder") //
                                .name("ClassWithCollections") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(ClassWithCollections.class.getName())) //
                                        .location(classLocation(ClassWithCollections.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .writeAccessor(Setter.builder().methodName("setInts").propertyName("ints").propertyType(new CollectionType(parameterize(Collection.class, Integer.class), Integer.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setList").propertyName("list").propertyType(new CollectionType(List.class, Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setSet").propertyName("set").propertyType(new CollectionType(setOfList, List.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setDeque").propertyName("deque").propertyType(new CollectionType(parameterize(Deque.class, wildcardType().withUpperBounds(Object.class).build()), wildcardType().withUpperBounds(Object.class).build())).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setSortedSetWild").propertyName("sortedSetWild").propertyType(new CollectionType(parameterize(SortedSet.class, wildcardType().build()), wildcardType().build())).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setFloats").propertyName("floats").propertyType(new ArrayType(float[].class, float.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setMap").propertyName("map").propertyType(new MapType(parameterize(Map.class, String.class, Object.class), String.class, Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setMapTU").propertyName("mapTU").propertyType(new MapType(parameterize(Map.class, typeVariableT(), typeVariableU()), typeVariableT(), typeVariableU())).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setMapWildObj").propertyName("mapWildObj").propertyType(new MapType(parameterize(Map.class, wildcardType().build(), Object.class), wildcardType().build(), Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setMapNoTypeArgs").propertyName("mapNoTypeArgs").propertyType(new MapType(Map.class, Object.class, Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setListWithTwoParams").propertyName("listWithTwoParams").propertyType(new CollectionType(parameterize(ListWithTwoParams.class, String.class, Integer.class), parameterize(Map.class, String.class, Integer.class))).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .writeAccessor(Setter.builder().methodName("setMapWithThreeParams").propertyName("mapWithThreeParams").propertyType(new MapType(parameterize(MapWithThreeParams.class, String.class, Integer.class, Boolean.class), String.class, parameterize(Map.class, Integer.class, Boolean.class))).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "io.github.tobi.laa.reflective.fluent.builders.test.models.visibility", //
                        "", //
                        "set", //
                        classInfo.get(PackagePrivateConstructor.class.getName()), //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility") //
                                .name("PackagePrivateConstructor0") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(PackagePrivateConstructor.class.getName())) //
                                        .location(classLocation(PackagePrivateConstructor.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setIntField") //
                                                .propertyName("intField") //                                                
                                                .propertyType(new SimpleType(int.class))
                                                .visibility(PUBLIC) //
                                                .declaringClass(PackagePrivateConstructor.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setPackagePrivate") //
                                                .propertyName("packagePrivate") //
                                                .propertyType(new SimpleType(packagePrivate))
                                                .visibility(PUBLIC) //
                                                .declaringClass(PackagePrivateConstructor.class) //
                                                .build()) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        BuilderConstants.PACKAGE_PLACEHOLDER, //
                        "Builder", //
                        "set", //
                        classInfo.get(SimpleClassNoSetPrefix.class.getName()), //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoSetPrefixBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClassNoSetPrefix.class.getName())) //
                                        .location(classLocation(SimpleClassNoSetPrefix.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        BuilderConstants.PACKAGE_PLACEHOLDER, //
                        "MyBuilderSuffix", //
                        "", //
                        classInfo.get(SimpleClassNoSetPrefix.class.getName()), //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoSetPrefixMyBuilderSuffix") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClassNoSetPrefix.class.getName())) //
                                        .location(classLocation(SimpleClassNoSetPrefix.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("anInt") //
                                                .propertyName("anInt") //
                                                .propertyType(new SimpleType(int.class))
                                                .visibility(PACKAGE_PRIVATE) //
                                                .declaringClass(SimpleClassNoSetPrefix.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("aString") //
                                                .propertyName("aString") //
                                                .propertyType(new SimpleType(String.class))
                                                .visibility(PACKAGE_PRIVATE) //
                                                .declaringClass(SimpleClassNoSetPrefix.class) //
                                                .build()) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "builders.io.github.tobi.laa.reflective.fluent.builders.test.models.simple", //
                        "Builder", //
                        "set", //
                        classInfo.get(SimpleClassNoDefaultConstructor.class.getName()), //
                        BuilderMetadata.builder() //
                                .packageName("builders.io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoDefaultConstructorBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClassNoDefaultConstructor.class.getName())) //
                                        .location(classLocation(SimpleClassNoDefaultConstructor.class)) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        BuilderConstants.PACKAGE_PLACEHOLDER, //
                        "Builder", //
                        "set", //
                        classInfo.get(NameCollisions.class), //
                        BuilderMetadata.builder() //
                                .packageName(NameCollisions.class.getPackageName()) //
                                .name("NameCollisionsBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(NameCollisions.class.getName())) //
                                        .location(classLocation(NameCollisions.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setField") //
                                                .propertyName("field") //
                                                .propertyType(new SimpleType(int.class))
                                                .visibility(PUBLIC) //
                                                .declaringClass(NameCollisions.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setField") //
                                                .propertyName("field0") //
                                                .propertyType(new SimpleType(String.class))
                                                .visibility(PUBLIC) //
                                                .declaringClass(NameCollisions.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setAnotherField") //
                                                .propertyName("anotherField") //
                                                .propertyType(new SimpleType(boolean.class))
                                                .visibility(PUBLIC) //
                                                .declaringClass(NameCollisions.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setAnotherField") //
                                                .propertyName("anotherField0") //
                                                .propertyType(new SimpleType(int.class))
                                                .visibility(PUBLIC) //
                                                .declaringClass(NameCollisions.class) //
                                                .build()) //
                                        .writeAccessor(Setter.builder() //
                                                .methodName("setAnotherField") //
                                                .propertyName("anotherField1") //
                                                .propertyType(new SimpleType(String.class))
                                                .visibility(PUBLIC) //
                                                .declaringClass(NameCollisions.class) //
                                                .build()) //
                                        .build()) //
                                .build()));
    }

    private static Path classLocation(final Class<?> clazz) {
        Path classLocation = TEST_MODELS_TARGET_DIR.resolve("classes");
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
        final var classes = Stream.of(Abstract.class, Annotation.class, Enum.class, Interface.class)
                .map(clazz -> classInfo.get(clazz.getName()))
                .collect(Collectors.toSet());
        // Act
        final Set<ClassInfo> actual = service.filterOutNonBuildableClasses(classes);
        // Assert
        assertThat(actual).isEmpty();
    }

    @Test
    @SneakyThrows
    void testFilterOutNonBuildableClassesInaccessibleClass() {
        // Arrange
        final var classes = Stream.of(
                        SimpleClass.class,
                        Class.forName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivate"))
                .map(clazz -> classInfo.get(clazz.getName()))
                .collect(Collectors.toSet());
        doReturn("a.package").when(properties).getBuilderPackage();
        // Act
        final Set<ClassInfo> actual = service.filterOutNonBuildableClasses(classes);
        // Assert
        assertThat(actual).containsExactly(classInfo.get(SimpleClass.class.getName()));
    }

    @Test
    void testFilterOutNonBuildableClassesNestedClasses() {
        // Arrange
        final var classes = Stream.of(TopLevelClass.NestedPublicLevelOne.class, TopLevelClass.NestedNonStatic.class)
                .map(clazz -> classInfo.get(clazz.getName()))
                .collect(Collectors.toSet());
        // Act
        final Set<ClassInfo> actual = service.filterOutNonBuildableClasses(classes);
        // Assert
        assertThat(actual).containsExactly(classInfo.get(TopLevelClass.NestedPublicLevelOne.class.getName()));
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
    void testFilterOutConfiguredExcludes(final Set<Predicate<Class<?>>> excludes, final Set<ClassInfo> classes, final Set<ClassInfo> expected) {
        // Arrange
        lenient().when(properties.getExcludes()).thenReturn(excludes);
        // Act
        final Set<ClassInfo> actual = service.filterOutConfiguredExcludes(classes);
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
                        Set.of(classInfo.get(SimpleClass.class.getName())), //
                        Set.of(classInfo.get(SimpleClass.class.getName()))), //
                Arguments.of( //
                        Set.<Predicate<Class<?>>>of(cl -> cl.getPackageName().equals(Complex.class.getPackageName())), //
                        Set.of(
                                classInfo.get(SimpleClass.class.getName()),
                                classInfo.get(ClassWithCollections.class.getName()),
                                classInfo.get(ClassWithGenerics.class.getName())), //
                        Set.of(classInfo.get(SimpleClass.class.getName()))), //
                Arguments.of( //
                        Set.<Predicate<Class<?>>>of(SimpleClass.class::equals), //
                        Set.of(
                                classInfo.get(SimpleClass.class.getName()),
                                classInfo.get(ClassWithCollections.class.getName()),
                                classInfo.get(ClassWithGenerics.class.getName())), //
                        Set.of(
                                classInfo.get(ClassWithCollections.class.getName()),
                                classInfo.get(ClassWithGenerics.class.getName()))), //
                Arguments.of( //
                        Set.<Predicate<Class<?>>>of(cl -> cl.getName().endsWith("Generics")), //
                        Set.of(
                                classInfo.get(SimpleClass.class.getName()),
                                classInfo.get(ClassWithCollections.class.getName()),
                                classInfo.get(ClassWithGenerics.class.getName())), //
                        Set.of(
                                classInfo.get(SimpleClass.class.getName()),
                                classInfo.get(ClassWithCollections.class.getName()))));
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
                                                .type(classInfo.get(SimpleClass.class.getName())) //
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
                                                .type(classInfo.get(SimpleClass.class.getName())) //
                                                .accessibleNonArgsConstructor(true) //
                                                .build()) //
                                        .build(),
                                BuilderMetadata.builder() //
                                        .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(classInfo.get(SimpleClass.class.getName())) //
                                                .accessibleNonArgsConstructor(true) //
                                                .writeAccessor(Setter.builder() //
                                                        .methodName("setPub") //
                                                        .propertyName("pub") //
                                                        .propertyType(new SimpleType(int.class)) //
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
                                                .type(classInfo.get(SimpleClass.class.getName())) //
                                                .accessibleNonArgsConstructor(true) //
                                                .writeAccessor(Setter.builder() //
                                                        .methodName("setPub") //
                                                        .propertyName("pub") //
                                                        .propertyType(new SimpleType(int.class)) //
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
                classInfo.get(SimpleClass.class.getName()), //
                classInfo.get(ClassWithBuilderExisting.class.getName()), //
                classInfo.get(ClassWithBuilderExisting.ClassWithBuilderExistingBuilder.class.getName()), //
                classInfo.get(HasTheSuffixBuilderImpl.class.getName())));
        // Assert
        assertThat(filteredClasses).contains(classInfo.get(SimpleClass.class.getName()), classInfo.get(ClassWithBuilderExisting.class.getName()));
    }

    private static TypeVariable<?> typeVariableT() {
        return ClassWithCollections.class.getTypeParameters()[0];
    }

    private static TypeVariable<?> typeVariableU() {
        return ClassWithCollections.class.getTypeParameters()[1];
    }

    @SuppressWarnings("unused")
    private static class ClassWithMarkerField {

        @SuppressWarnings("java:S116")
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
