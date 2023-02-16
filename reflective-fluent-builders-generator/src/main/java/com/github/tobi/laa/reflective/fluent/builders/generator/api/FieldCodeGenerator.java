package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.FieldSpec;

/**
 * <p>
 * Generates the {@link FieldSpec} for a single field of a builder. The specifics of which field is being generated
 * depend on the implementation.
 * </p>
 */
public interface FieldCodeGenerator {

    /**
     * <p>
     * Generates the {@link FieldSpec} for a single field of the builder corresponding to {@code builderMetadata}. The
     * specifics of which field is being generated depend on the implementation.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate said field. Must not be {@code null}.
     * @return The generated field.
     */
    FieldSpec generate(final BuilderMetadata builderMetadata);
}
