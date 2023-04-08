package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.AnnotationSpec;

/**
 * <p>
 * Generates an {@link AnnotationSpec} to be placed on the class declaration of a {@link BuilderMetadata builder}.
 * </p>
 */
public interface AnnotationCodeGenerator {

    /**
     * <p>
     * Generates an {@link AnnotationSpec} to be placed on the class declaration of a {@code builderMetadata}.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate said annotation. Must not be
     *                        {@code null}.
     * @return The annotation.
     */
    AnnotationSpec generate(final BuilderMetadata builderMetadata);
}
