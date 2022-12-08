package com.github.tobi.laa.fluent.builder.generator.service.impl;

import com.github.tobi.laa.fluent.builder.generator.service.api.VisibilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BuilderServiceImplTest {

    @Mock
    private VisibilityService visibilityService;

    @Test
    void testCollectBuilderMetadataNull() {
        // Arrange
        final var builderService = new BuilderServiceImpl(visibilityService, "", "");
        // Act
        final Executable collectBuilderMetadata = () -> builderService.collectBuilderMetadata(null);
        // Assert
        assertThrows(NullPointerException.class, collectBuilderMetadata);
    }

    @Test
    void testCollectBuilderMetadata() {
    }


    @Test
    void testFilterOutNonBuildableClassesNull() {
        // Arrange
        final var builderService = new BuilderServiceImpl(visibilityService, "", "");
        // Act
        final Executable filterOutNonBuildableClasses = () -> builderService.filterOutNonBuildableClasses(null);
        // Assert
        assertThrows(NullPointerException.class, filterOutNonBuildableClasses);
    }

    @Test
    void testFilterOutNonBuildableClasses() {
    }
}