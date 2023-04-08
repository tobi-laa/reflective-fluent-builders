package io.github.tobi.laa.reflective.fluent.builders.service.api;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithBuilderExisting;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import lombok.SneakyThrows;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BuilderMetadataServiceIT {

    private BuilderMetadataService builderMetadataService;

    @BeforeEach
    @SneakyThrows
    void init() {
        final ClassLoader classloader = getClass().getClassLoader();
        final Injector injector = Guice.createInjector(
                new WireModule(
                        new SpaceModule(new URLClassSpace(classloader))));
        builderMetadataService = (BuilderMetadataService) injector.getInstance(Class.forName("io.github.tobi.laa.reflective.fluent.builders.service.impl.BuilderMetadataServiceImpl"));
    }

    @Test
    void testFilterOutConfiguredExcludesWithDefaultConfig() {
        // Act
        final Set<Class<?>> filteredClasses = builderMetadataService.filterOutConfiguredExcludes(ImmutableSet.of( //
                SimpleClass.class, //
                ClassWithBuilderExisting.class, //
                ClassWithBuilderExisting.ClassWithBuilderExistingBuilder.class, //
                HasTheSuffixBuilderImpl.class));
        // Assert
        assertThat(filteredClasses).contains(SimpleClass.class, ClassWithBuilderExisting.class);
    }

    static class HasTheSuffixBuilderImpl {
        // no content
    }
}
