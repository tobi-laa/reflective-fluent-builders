package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.TypeName;

/**
 * <p>
 * Generates the full {@link TypeName} (including generics) for a given {@link Setter setter's}
 * {@link Setter#getParamType() param}.
 * </p>
 */
public interface SetterTypeNameGenerator {

    /**
     * <p>
     * Generates the full {@link TypeName} (including generics) for the {@link Setter#getParamType() param} of
     * {@code setter}.
     * </p>
     *
     * @param setter The setter for whose param to generate the full type name. Must not be {@code null}.
     * @return The full {@link TypeName} (including generics) for the {@link Setter#getParamType() param} of
     * {@code setter}.
     */
    TypeName generateTypeNameForParam(final Setter setter);
}
