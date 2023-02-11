package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.MethodSpec;

/**
 * <p>
 * Generates the {@link MethodSpec} for a single fluent {@link Setter setter}.
 * </p>
 */
public interface FluentSetterCodeGenerator {

    /**
     * <p>
     * Generates the {@link MethodSpec} for the fluent setter method for {@code setter}.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate said method. Must not be {@code null}.
     * @param setter          The setter for which to generate said method. Must not be {@code null}.
     * @return The method for the fluent setter for {@code setter}.
     */
    MethodSpec generateFluentSetter(final BuilderMetadata builderMetadata, final Setter setter);
}
