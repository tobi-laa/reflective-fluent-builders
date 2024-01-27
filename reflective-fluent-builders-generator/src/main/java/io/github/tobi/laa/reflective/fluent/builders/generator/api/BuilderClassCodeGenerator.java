package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.TypeSpec;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

/**
 * <p>
 * Generated the spec for a builder class from {@link BuilderMetadata builder metadata}.
 * </p>
 */
public interface BuilderClassCodeGenerator {

    /**
     * <p>
     * Generates a {@link TypeSpec} containing the actual builder class corresponding to {@code builderMetadata}.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate a {@link TypeSpec}. Must not be
     *                        {@code null}.
     * @return A {@link TypeSpec} containing the actual builder class corresponding to {@code builderMetadata}.
     * @throws CodeGenerationException If an error occurs during code generation.
     */
    TypeSpec generateBuilderClass(final BuilderMetadata builderMetadata);
}