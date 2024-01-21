package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.TypeName;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

import java.lang.reflect.Type;

/**
 * <p>
 * Generates the full {@link TypeName} (including generics) for a given {@link WriteAccessor write accessor's}
 * {@link WriteAccessor#getPropertyType() type} or a given {@link Type}.
 * </p>
 */
public interface TypeNameGenerator {

    /**
     * <p>
     * Generates the full {@link TypeName} (including generics) for the {@link WriteAccessor#getPropertyType() type} of
     * {@code writeAccessor}.
     * </p>
     *
     * @param writeAccessor The write accessor for whose property to generate the full type name. Must not be
     *                      {@code null}.
     * @return The full {@link TypeName} (including generics) for the {@link WriteAccessor#getPropertyType() property}
     * of {@code writeAccessor}.
     */
    TypeName generateTypeName(final WriteAccessor writeAccessor);

    /**
     * <p>
     * Generates a {@link TypeName} for {@code type} that can safely be used as a parameter.
     * </p>
     *
     * @param type The type for which to generate the type name. Must not be {@code null}.
     * @return The {@link TypeName} for {@code type} that can safely be used as a parameter.
     */
    TypeName generateTypeName(final Type type);
}
