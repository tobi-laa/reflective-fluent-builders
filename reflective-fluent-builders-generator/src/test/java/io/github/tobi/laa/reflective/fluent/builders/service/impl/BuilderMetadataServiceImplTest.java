package io.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.google.common.collect.ImmutableSortedSet;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.model.SimpleSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithGenerics;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.Complex;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.TopLevelClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleAbstractClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoDefaultConstructor;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Abstract;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Annotation;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Enum;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Interface;
import io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivateConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuilderMetadataServiceImplTest {

    @InjectMocks
    private BuilderMetadataServiceImpl builderService;

    @Mock
    private VisibilityService visibilityService;

    @Mock
    private SetterService setterService;

    @Mock
    private ClassService classService;

    @Mock
    private BuildersProperties properties;

    @Test
    void testCollectBuilderMetadataNull() {
        // Act
        final Executable collectBuilderMetadata = () -> builderService.collectBuilderMetadata(null);
        // Assert
        assertThrows(NullPointerException.class, collectBuilderMetadata);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectBuilderMetadata(final String builderPackage, final String builderSuffix,
                                    final Visibility[] visibility, final SortedSet<Setter> setters,
                                    final Class<?> clazz, final Path location, final BuilderMetadata expected) {
        // Arrange
        when(properties.getBuilderPackage()).thenReturn(builderPackage);
        when(properties.getBuilderSuffix()).thenReturn(builderSuffix);
        when(visibilityService.toVisibility(anyInt())).thenReturn(visibility[0], ArrayUtils.remove(visibility, 0));
        when(setterService.gatherAllSetters(clazz)).thenReturn(setters);
        when(classService.determineClassLocation(clazz)).thenReturn(Optional.ofNullable(location));
        // Act
        final BuilderMetadata actual = builderService.collectBuilderMetadata(clazz);
        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @SneakyThrows
    private static Stream<Arguments> testCollectBuilderMetadata() {
        final Setter privateSetter = SimpleSetter.builder().methodName("setPriv").paramName("priv").paramType(int.class).visibility(Visibility.PRIVATE).declaringClass(SimpleClass.class).build();
        final Setter packagePrivateSetter = SimpleSetter.builder().methodName("setPack").paramName("pack").paramType(char.class).visibility(Visibility.PACKAGE_PRIVATE).declaringClass(SimpleClass.class).build();
        final Setter protectedSetter = SimpleSetter.builder().methodName("setProt").paramName("prot").paramType(Object.class).visibility(Visibility.PROTECTED).declaringClass(SimpleClass.class).build();
        final Setter protectedSetterFromAbstractClass = SimpleSetter.builder().methodName("setProtAbst").paramName("protAbst").paramType(Object.class).visibility(Visibility.PROTECTED).declaringClass(SimpleAbstractClass.class).build();
        final Setter packagePrivateSetterFromAbstractClass = SimpleSetter.builder().methodName("setPackAbst").paramName("packAbst").paramType(char.class).visibility(Visibility.PACKAGE_PRIVATE).declaringClass(SimpleAbstractClass.class).build();
        final Setter publicSetter = SimpleSetter.builder().methodName("setPub").paramName("pub").paramType(int.class).visibility(Visibility.PUBLIC).declaringClass(SimpleClass.class).build();
        final Setter setterNameCollision1 = SimpleSetter.builder().methodName("setPub").paramName("pub").paramType(Object.class).visibility(Visibility.PUBLIC).declaringClass(SimpleClass.class).build();
        final Setter setterNameCollision2 = SimpleSetter.builder().methodName("setPub").paramName("pub").paramType(String.class).visibility(Visibility.PUBLIC).declaringClass(SimpleClass.class).build();
        final var packagePrivate = Class.forName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.PackagePrivate");
        final var aPath = Paths.get("");
        final var anotherPath = Paths.get("another/path");
        return Stream.of( //
                Arguments.of( //
                        "<PACKAGE_NAME>", //
                        "Builder", //
                        new Visibility[]{Visibility.PACKAGE_PRIVATE, Visibility.PUBLIC, Visibility.PUBLIC, Visibility.PUBLIC, Visibility.PUBLIC, Visibility.PUBLIC, Visibility.PUBLIC}, //
                        ImmutableSortedSet.of(privateSetter, packagePrivateSetter, protectedSetter, protectedSetterFromAbstractClass, packagePrivateSetterFromAbstractClass, publicSetter, setterNameCollision1, setterNameCollision2), //
                        SimpleClass.class, //
                        null, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
                                        .location(null) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(packagePrivateSetter) //
                                        .setter(protectedSetter) //
                                        .setter(protectedSetterFromAbstractClass) //
                                        .setter(publicSetter) //
                                        .setter(setterNameCollision1.withParamName("pub0")) //
                                        .setter(setterNameCollision2.withParamName("pub1")) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "<PACKAGE_NAME>.builder", //
                        "", //
                        new Visibility[]{Visibility.PACKAGE_PRIVATE, Visibility.PUBLIC, Visibility.PUBLIC, Visibility.PUBLIC}, //
                        ImmutableSortedSet.of(privateSetter, packagePrivateSetter, protectedSetter, publicSetter), //
                        ClassWithCollections.class, //
                        aPath, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.complex.builder") //
                                .name("ClassWithCollections") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(ClassWithCollections.class) //
                                        .location(aPath) //
                                        .accessibleNonArgsConstructor(false) //
                                        .setter(publicSetter) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "<PACKAGE_NAME>.builder", //
                        "", //
                        new Visibility[]{Visibility.PACKAGE_PRIVATE, Visibility.PUBLIC, Visibility.PACKAGE_PRIVATE}, //
                        ImmutableSortedSet.of(
                                publicSetter, //
                                SimpleSetter.builder() //
                                        .methodName("setPackagePrivate") //
                                        .paramName("packagePrivate") //
                                        .paramType(packagePrivate) //
                                        .visibility(Visibility.PUBLIC) //
                                        .declaringClass(PackagePrivateConstructor.class) //
                                        .build()), //
                        PackagePrivateConstructor.class, //
                        anotherPath, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.builder") //
                                .name("PackagePrivateConstructor") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(PackagePrivateConstructor.class) //
                                        .location(anotherPath) //
                                        .accessibleNonArgsConstructor(false) //
                                        .setter(publicSetter) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "<PACKAGE_NAME>", //
                        "", //
                        new Visibility[]{Visibility.PACKAGE_PRIVATE, Visibility.PUBLIC, Visibility.PACKAGE_PRIVATE}, //
                        ImmutableSortedSet.of(
                                publicSetter, //
                                SimpleSetter.builder() //
                                        .methodName("setPackagePrivate") //
                                        .paramName("packagePrivate") //
                                        .paramType(packagePrivate) //
                                        .visibility(Visibility.PUBLIC) //
                                        .declaringClass(PackagePrivateConstructor.class) //
                                        .build()), //
                        PackagePrivateConstructor.class, //
                        null, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility") //
                                .name("PackagePrivateConstructor") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(PackagePrivateConstructor.class) //
                                        .location(null) //
                                        .accessibleNonArgsConstructor(false) //
                                        .setter(publicSetter) //
                                        .setter(SimpleSetter.builder() //
                                                .methodName("setPackagePrivate") //
                                                .paramName("packagePrivate") //
                                                .paramType(packagePrivate) //
                                                .visibility(Visibility.PUBLIC) //
                                                .declaringClass(PackagePrivateConstructor.class) //
                                                .build()) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "io.github.tobi.laa.reflective.fluent.builders.test.models.visibility", //
                        "", //
                        new Visibility[]{Visibility.PACKAGE_PRIVATE, Visibility.PRIVATE, Visibility.PACKAGE_PRIVATE}, //
                        ImmutableSortedSet.of(
                                publicSetter, //
                                SimpleSetter.builder() //
                                        .methodName("setPackagePrivate") //
                                        .paramName("packagePrivate") //
                                        .paramType(packagePrivate) //
                                        .visibility(Visibility.PUBLIC) //
                                        .declaringClass(PackagePrivateConstructor.class) //
                                        .build()), //
                        PackagePrivateConstructor.class, //
                        null, //
                        BuilderMetadata.builder() //
                                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.visibility") //
                                .name("PackagePrivateConstructor") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(PackagePrivateConstructor.class) //
                                        .location(null) //
                                        .accessibleNonArgsConstructor(false) //
                                        .setter(SimpleSetter.builder() //
                                                .methodName("setPackagePrivate") //
                                                .paramName("packagePrivate") //
                                                .paramType(packagePrivate) //
                                                .visibility(Visibility.PUBLIC) //
                                                .declaringClass(PackagePrivateConstructor.class) //
                                                .build()) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "the.builder.package", //
                        "MyBuilderSuffix", //
                        new Visibility[]{Visibility.PUBLIC, Visibility.PUBLIC, Visibility.PUBLIC}, //
                        ImmutableSortedSet.of(privateSetter, protectedSetter), //
                        SimpleClassNoSetPrefix.class, //
                        aPath, //
                        BuilderMetadata.builder() //
                                .packageName("the.builder.package") //
                                .name("SimpleClassNoSetPrefixMyBuilderSuffix") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClassNoSetPrefix.class) //
                                        .location(aPath) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build()), //
                Arguments.of( //
                        "builders.<PACKAGE_NAME>", //
                        "Builder", //
                        new Visibility[]{Visibility.PUBLIC}, //
                        Collections.emptySortedSet(), //
                        SimpleClassNoDefaultConstructor.class, //
                        anotherPath, //
                        BuilderMetadata.builder() //
                                .packageName("builders.io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoDefaultConstructorBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClassNoDefaultConstructor.class) //
                                        .location(anotherPath) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build()) //
        );
    }

    @Test
    void testFilterOutNonBuildableClassesNull() {
        // Act
        final Executable filterOutNonBuildableClasses = () -> builderService.filterOutNonBuildableClasses(null);
        // Assert
        assertThrows(NullPointerException.class, filterOutNonBuildableClasses);
    }

    @ParameterizedTest
    @MethodSource
    void testFilterOutNonBuildableClasses(final String builderPackage, final Visibility classVisibility,
                                          final Set<Class<?>> classes, final Set<Class<?>> expected) {
        // Arrange
        lenient().when(properties.getBuilderPackage()).thenReturn(builderPackage);
        lenient().when(visibilityService.toVisibility(anyInt())).thenReturn(classVisibility);
        // Act
        final Set<Class<?>> actual = builderService.filterOutNonBuildableClasses(classes);
        // Assert
        assertEquals(expected, actual);
    }

    @SneakyThrows
    private static Stream<Arguments> testFilterOutNonBuildableClasses() {
        return Stream.of( //
                Arguments.of( //
                        "<PACKAGE_NAME>", //
                        Visibility.PUBLIC, //
                        Set.of(Abstract.class, Annotation.class, Enum.class, Interface.class),
                        Collections.emptySet()), //
                Arguments.of( //
                        "<PACKAGE_NAME>", //
                        Visibility.PACKAGE_PRIVATE, //
                        Set.of(SimpleClass.class),
                        Set.of(SimpleClass.class)), //
                Arguments.of( //
                        SimpleClass.class.getPackageName(), //
                        Visibility.PACKAGE_PRIVATE, //
                        Set.of(SimpleClass.class),
                        Set.of(SimpleClass.class)), //
                Arguments.of( //
                        "<PACKAGE_NAME>.builder", //
                        Visibility.PACKAGE_PRIVATE, //
                        Set.of(SimpleClass.class),
                        Collections.emptySet()),
                Arguments.of( //
                        "<PACKAGE_NAME>", //
                        Visibility.PUBLIC, //
                        Set.of(TopLevelClass.NestedPublicLevelOne.class, TopLevelClass.NestedNonStatic.class),
                        Set.of(TopLevelClass.NestedPublicLevelOne.class)));
    }

    @Test
    void testFilterOutConfiguredExcludesNull() {
        // Act
        final Executable filterOutConfiguredExcludes = () -> builderService.filterOutConfiguredExcludes(null);
        // Assert
        assertThrows(NullPointerException.class, filterOutConfiguredExcludes);
    }

    @ParameterizedTest
    @MethodSource
    void testFilterOutConfiguredExcludes(final Set<Predicate<Class<?>>> excludes, final Set<Class<?>> classes, final Set<Class<?>> expected) {
        // Arrange
        lenient().when(properties.getExcludes()).thenReturn(excludes);
        // Act
        final Set<Class<?>> actual = builderService.filterOutConfiguredExcludes(classes);
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
        final Executable filterOutEmptyBuilders = () -> builderService.filterOutEmptyBuilders(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, filterOutEmptyBuilders);
    }

    @ParameterizedTest
    @MethodSource
    void testFilterOutEmptyBuilders(final Collection<BuilderMetadata> builderMetadata, final Set<BuilderMetadata> expected) {
        // Act
        final Set<BuilderMetadata> actual = builderService.filterOutEmptyBuilders(builderMetadata);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testFilterOutEmptyBuilders() {
        return Stream.of( //
                Arguments.of(Collections.emptySet(), Collections.emptySet()), //
                Arguments.of(
                        Collections.singleton( //
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
                                                        .visibility(Visibility.PUBLIC) //
                                                        .declaringClass(SimpleClass.class) //
                                                        .build())
                                                .build()) //
                                        .build()),
                        Collections.singleton( //
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
                                                        .visibility(Visibility.PUBLIC) //
                                                        .declaringClass(SimpleClass.class) //
                                                        .build())
                                                .build()) //
                                        .build())));
    }
}