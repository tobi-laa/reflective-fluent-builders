package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.CodeBlock;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.model.MapType;

/**
 * <p>
 * Generates a {@link CodeBlock} for initializing the map represented by a given {@link MapType}.
 * </p>
 * <p>
 * Calling {@link #generateMapInitializer(MapType)} for a {@link java.util.Map Map} should for instance produce a
 * CodeBlock holding the statement {@code "new HashMap<>()"}.
 * </p>
 */
public interface MapInitializerCodeGenerator {

    /**
     * <p>
     * Returns {@code true} if this generator is applicable for {@code mapType} (meaning it can be used for
     * generating an initializer) and {@code false} otherwise.
     * </p>
     *
     * @param mapType The type for which to check whether this generator is applicable. Must not be {@code null}.
     * @return {@code true} if this generator is applicable for {@code mapType} (meaning it can be used for generating
     * an initializer) and {@code false} otherwise.
     */
    boolean isApplicable(final MapType mapType);

    /**
     * <p>
     * Generates a {@link CodeBlock} for initializing the map represented by a given {@link MapType}.
     * </p>
     * <p>
     * Calling this method for a {@link java.util.Map Map} should for instance produce a CodeBlock holding the statement
     * {@code "new HashMap<>()"}.
     * </p>
     *
     * @param mapType The type for which to generate the code block for initializing the underlying map. Must not be
     *                {@code null}.
     * @return A code block for initializing the map represented by {@code mapType}.
     * @throws CodeGenerationException If called for a type for which this generator is not
     *                                 {@link #isApplicable(MapType) applicable}.
     */
    CodeBlock generateMapInitializer(final MapType mapType);
}
