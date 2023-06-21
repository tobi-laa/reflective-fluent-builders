package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.TypeName;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;

import java.lang.reflect.Type;

/**
 * <p>
 * Generates the full {@link TypeName} (including generics) for a given {@link Setter setter's}
 * {@link Setter#getParamType() param} or a given {@link Type}.
 * </p>
 */
public interface TypeNameGenerator {

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

    /**
     * <p>
     * Generates a {@link TypeName} for {@code type} that can safely be used as a parameter.
     * </p>
     *
     * @param type The type for which to generate the type name. Must not be {@code null}.
     * @return The {@link TypeName} for {@code type} that can safely be used as a parameter.
     */
    TypeName generateTypeNameForParam(final Type type);
}
