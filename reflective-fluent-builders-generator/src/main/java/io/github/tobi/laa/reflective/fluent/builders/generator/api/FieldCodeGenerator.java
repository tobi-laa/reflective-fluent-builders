package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.FieldSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

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
