package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

/**
 * <p>
 * Generates the <em>name</em> of a single fluent setter method of a builder.
 * </p>
 *
 * @see SetterCodeGenerator
 */
public interface SetterMethodNameGenerator {

    /**
     * <p>
     * Generates the <em>name</em> of the fluent setter method for {@code writeAccessor}.
     * </p>
     *
     * @param writeAccessor The write accessor for which to generate the name. Must not be {@code null}.
     * @return The name of the fluent setter for {@code writeAccessor}.
     */
    String generate(final WriteAccessor writeAccessor);
}
