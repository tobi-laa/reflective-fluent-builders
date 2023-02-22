package com.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.github.tobi.laa.reflective.fluent.builders.model.SimpleSetter;
import com.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import com.github.tobi.laa.reflective.fluent.builders.test.models.nested.TopLevelClass;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoDefaultConstructor;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import com.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Abstract;
import com.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Annotation;
import com.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Enum;
import com.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Interface;
import com.google.common.collect.ImmutableSortedSet;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
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
                                    final Visibility constructorVisibility, final SortedSet<Setter> setters,
                                    final Class<?> clazz, final BuilderMetadata expected) {
        // Arrange
        when(properties.getBuilderPackage()).thenReturn(builderPackage);
        when(properties.getBuilderSuffix()).thenReturn(builderSuffix);
        when(visibilityService.toVisibility(anyInt())).thenReturn(constructorVisibility);
        when(setterService.gatherAllSetters(clazz)).thenReturn(setters);
        // Act
        final BuilderMetadata actual = builderService.collectBuilderMetadata(clazz);
        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private static Stream<Arguments> testCollectBuilderMetadata() {
        final Setter privateSetter = SimpleSetter.builder().methodName("setPriv").paramName("priv").paramType(int.class).visibility(Visibility.PRIVATE).build();
        final Setter packagePrivateSetter = SimpleSetter.builder().methodName("setPack").paramName("pack").paramType(char.class).visibility(Visibility.PACKAGE_PRIVATE).build();
        final Setter protectedSetter = SimpleSetter.builder().methodName("setProt").paramName("prot").paramType(Object.class).visibility(Visibility.PROTECTED).build();
        final Setter publicSetter = SimpleSetter.builder().methodName("setPub").paramName("pub").paramType(int.class).visibility(Visibility.PUBLIC).build();
        final Setter setterNameCollision1 = SimpleSetter.builder().methodName("setPub").paramName("pub").paramType(Object.class).visibility(Visibility.PUBLIC).build();
        final Setter setterNameCollision2 = SimpleSetter.builder().methodName("setPub").paramName("pub").paramType(String.class).visibility(Visibility.PUBLIC).build();
        return Stream.of( //
                Arguments.of( //
                        "<PACKAGE_NAME>", //
                        "Builder", //
                        Visibility.PACKAGE_PRIVATE, //
                        ImmutableSortedSet.of(privateSetter, packagePrivateSetter, protectedSetter, publicSetter, setterNameCollision1, setterNameCollision2), //
                        SimpleClass.class, //
                        BuilderMetadata.builder() //
                                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClass.class) //
                                        .accessibleNonArgsConstructor(true) //
                                        .setter(packagePrivateSetter) //
                                        .setter(publicSetter) //
                                        .setter(setterNameCollision1.withParamName("pub0")) //
                                        .setter(setterNameCollision2.withParamName("pub1")) //
                                        .build()) //
                                .build() //
                ), //
                Arguments.of( //
                        "<PACKAGE_NAME>.builder", //
                        "", //
                        Visibility.PACKAGE_PRIVATE, //
                        ImmutableSortedSet.of(privateSetter, packagePrivateSetter, protectedSetter, publicSetter), //
                        ClassWithCollections.class, //
                        BuilderMetadata.builder() //
                                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.complex.builder") //
                                .name("ClassWithCollections") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(ClassWithCollections.class) //
                                        .accessibleNonArgsConstructor(false) //
                                        .setter(publicSetter) //
                                        .build()) //
                                .build() //
                ), //
                Arguments.of( //
                        "the.builder.package", //
                        "MyBuilderSuffix", //
                        Visibility.PUBLIC, //
                        ImmutableSortedSet.of(privateSetter, protectedSetter), //
                        SimpleClassNoSetPrefix.class, //
                        BuilderMetadata.builder() //
                                .packageName("the.builder.package") //
                                .name("SimpleClassNoSetPrefixMyBuilderSuffix") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClassNoSetPrefix.class) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build() //
                ),
                Arguments.of( //
                        "builders.<PACKAGE_NAME>", //
                        "Builder", //
                        Visibility.PUBLIC, //
                        Collections.emptySortedSet(), //
                        SimpleClassNoDefaultConstructor.class, //
                        BuilderMetadata.builder() //
                                .packageName("builders.com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                .name("SimpleClassNoDefaultConstructorBuilder") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(SimpleClassNoDefaultConstructor.class) //
                                        .accessibleNonArgsConstructor(false) //
                                        .build()) //
                                .build() //
                ));
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
                                        .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
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
                                        .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(SimpleClass.class) //
                                                .accessibleNonArgsConstructor(true) //
                                                .build()) //
                                        .build(),
                                BuilderMetadata.builder() //
                                        .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(SimpleClass.class) //
                                                .accessibleNonArgsConstructor(true) //
                                                .setter(SimpleSetter.builder() //
                                                        .methodName("setPub") //
                                                        .paramName("pub") //
                                                        .paramType(int.class) //
                                                        .visibility(Visibility.PUBLIC) //
                                                        .build())
                                                .build()) //
                                        .build()),
                        Collections.singleton( //
                                BuilderMetadata.builder() //
                                        .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                                        .name("SimpleClassBuilder") //
                                        .builtType(BuilderMetadata.BuiltType.builder() //
                                                .type(SimpleClass.class) //
                                                .accessibleNonArgsConstructor(true) //
                                                .setter(SimpleSetter.builder() //
                                                        .methodName("setPub") //
                                                        .paramName("pub") //
                                                        .paramType(int.class) //
                                                        .visibility(Visibility.PUBLIC) //
                                                        .build())
                                                .build()) //
                                        .build())));
    }
}