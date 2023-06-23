package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

import java.util.Optional;

/**
 * <p>
 * Generates a single {@link MethodSpec method} for a {@link BuilderMetadata builder}.
 * </p>
 */
public interface MethodCodeGenerator {

    /**
     * <p>
     * Generates a single {@link MethodSpec method} for a {@code builderMetadata}. Implementations may choose to
     * return an {@link Optional#isEmpty() empty optional} in cases where the generator is not applicable or cannot
     * generate the method.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate said method. Must not be {@code null}.
     * @return A constructor for {@code builderMetadata} or an {@link Optional#isEmpty() empty optional} in cases where
     * the generator is not applicable or cannot generate the method.
     */
    Optional<MethodSpec> generate(final BuilderMetadata builderMetadata);
}
