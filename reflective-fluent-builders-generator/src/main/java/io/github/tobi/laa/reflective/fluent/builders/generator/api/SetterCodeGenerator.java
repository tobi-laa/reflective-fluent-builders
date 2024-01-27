package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

/**
 * <p>
 * Generates the {@link MethodSpec} for a single fluent setter of a builder.
 * </p>
 */
public interface SetterCodeGenerator {

    /**
     * <p>
     * Generates the {@link MethodSpec} for the fluent setter method for {@code writeAccessor}.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate said method. Must not be {@code null}.
     * @param writeAccessor   The write accessor for which to generate said method. Must not be {@code null}.
     * @return The method for the fluent setter for {@code writeAccessor}.
     */
    MethodSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor);
}
