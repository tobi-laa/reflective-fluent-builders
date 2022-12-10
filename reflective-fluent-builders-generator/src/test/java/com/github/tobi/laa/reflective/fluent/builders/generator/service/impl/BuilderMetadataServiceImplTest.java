package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.Visibility;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.VisibilityService;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import com.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Abstract;
import com.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.*;
import com.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Enum;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BuilderMetadataServiceImplTest {

    @Mock
    private VisibilityService visibilityService;

    @Test
    void testCollectBuilderMetadataNull() {
        // Arrange
        final var builderService = new BuilderMetadataServiceImpl(visibilityService, "", "");
        // Act
        final Executable collectBuilderMetadata = () -> builderService.collectBuilderMetadata(null);
        // Assert
        assertThrows(NullPointerException.class, collectBuilderMetadata);
    }

    @ParameterizedTest
    @MethodSource
    void testCollectBuilderMetadata(final String builderPackage, final String builderSuffix,
                                    final Visibility constructorVisibility, final Class<?> clazz, final BuilderMetadata expected) {
        // Arrange
        final var builderService = new BuilderMetadataServiceImpl(visibilityService, builderPackage, builderSuffix);
        when(visibilityService.toVisibility(anyInt())).thenReturn(constructorVisibility);
        // Act
        final BuilderMetadata actual = builderService.collectBuilderMetadata(clazz);
        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private static Stream<Arguments> testCollectBuilderMetadata() {
        return Stream.of( //
                Arguments.of(
                        "<PACKAGE_NAME>", "Builder", Visibility.PACKAGE_PRIVATE, SimpleClass.class, //
                        BuilderMetadata.builder()
                                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple")
                                .name("SimpleClassBuilder")
                                .builtType(BuilderMetadata.BuiltType.builder()
                                        .type(SimpleClass.class)
                                        .accessibleNonArgsConstructor(true)
                                        .build())
                                .build()
                ), //
                Arguments.of(
                        "<PACKAGE_NAME>.builder", "", Visibility.PACKAGE_PRIVATE, ClassWithCollections.class, //
                        BuilderMetadata.builder()
                                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.complex.builder")
                                .name("ClassWithCollections")
                                .builtType(BuilderMetadata.BuiltType.builder()
                                        .type(ClassWithCollections.class)
                                        .accessibleNonArgsConstructor(false)
                                        .build())
                                .build()
                ), //
                Arguments.of(
                        "the.builder.package", "MyBuilderSuffix", Visibility.PUBLIC, SimpleClassNoSetPrefix.class, //
                        BuilderMetadata.builder()
                                .packageName("the.builder.package")
                                .name("SimpleClassNoSetPrefixMyBuilderSuffix")
                                .builtType(BuilderMetadata.BuiltType.builder()
                                        .type(SimpleClassNoSetPrefix.class)
                                        .accessibleNonArgsConstructor(true)
                                        .build())
                                .build()
                ));
    }

    @Test
    void testFilterOutNonBuildableClassesNull() {
        // Arrange
        final var builderService = new BuilderMetadataServiceImpl(visibilityService, "", "");
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
        final var builderService = new BuilderMetadataServiceImpl(visibilityService, builderPackage, "");
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
                        "<PACKAGE_NAME>.builder", //
                        Visibility.PACKAGE_PRIVATE, //
                        Set.of(SimpleClass.class),
                        Collections.emptySet()));
    }
}