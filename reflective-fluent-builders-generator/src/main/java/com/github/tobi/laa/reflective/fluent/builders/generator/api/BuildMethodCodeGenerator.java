package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.MethodSpec;

/**
 * <p>
 * Generates the {@code build()}-{@link MethodSpec method} of a {@link BuilderMetadata builder}.
 * </p>
 */
public interface BuildMethodCodeGenerator {

    /**
     * <p>
     * Generates the {@code build()}-{@link MethodSpec method} for {@code builderMetadata}.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate said method. Must not be {@code null}.
     * @return Said method for {@code builderMetadata}.
     */
    MethodSpec generateBuildMethod(final BuilderMetadata builderMetadata);
}
