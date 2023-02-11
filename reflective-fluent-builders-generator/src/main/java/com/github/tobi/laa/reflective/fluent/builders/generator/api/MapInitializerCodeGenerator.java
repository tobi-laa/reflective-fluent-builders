package com.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.model.MapSetter;
import com.squareup.javapoet.CodeBlock;

/**
 * <p>
 * Generates a {@link CodeBlock} for initializing the map being set by a given {@link MapSetter}.
 * </p>
 * <p>
 * Calling {@link #generateMapInitializer(MapSetter)} for the setter of a {@link java.util.Map Map}
 * should for instance produce a CodeBlock holding the statement {@code "new HashMap<>()"}.
 * </p>
 */
public interface MapInitializerCodeGenerator {

    /**
     * <p>
     * Returns {@code true} if this generator is applicable for {@code mapSetter} (meaning it can be used for
     * generating an initializer) and {@code false} otherwise.
     * </p>
     *
     * @param mapSetter The setter for which to check whether this generator is applicable. Must not be
     *                  {@code null}.
     * @return {@code true} if this generator is applicable for {@code mapSetter} (meaning it can be used for
     * generating an initializer) and {@code false} otherwise.
     */
    boolean isApplicable(final MapSetter mapSetter);

    /**
     * <p>
     * Generates a {@link CodeBlock} for initializing the map being set by a given {@link MapSetter}.
     * </p>
     * <p>
     * Calling this method for the setter of a {@link java.util.Map Map} should for instance produce a CodeBlock
     * holding the statement {@code "new HashMap<>()"}.
     * </p>
     *
     * @param mapSetter The setter for which to generate the code block for initializing the map being set by it. Must not be {@code null}.
     * @return A code block for initializing the map being set {@code mapSetter}.
     * @throws CodeGenerationException If called for a setter for which this generator is not {@link #isApplicable(MapSetter)}.
     */
    CodeBlock generateMapInitializer(final MapSetter mapSetter);
}
