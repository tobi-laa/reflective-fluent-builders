package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

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
