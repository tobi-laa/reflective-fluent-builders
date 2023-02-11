package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.ClassName;

/**
 * <p>
 * Generates a {@link BuilderMetadata builder's} {@link ClassName class name}.
 * </p>
 */
public interface BuilderClassNameGenerator {

    /**
     * <p>
     * Generates the {@link ClassName class name} for {@code builderMetadata}.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate the class name. Must not be {@code null}.
     * @return The class name for {@code builderMetadata}.
     */
    ClassName generateClassName(final BuilderMetadata builderMetadata);
}
