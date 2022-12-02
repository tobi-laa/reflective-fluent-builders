package com.github.tobi.laa.fluent.builder.maven.plugin.service.api;

/**
 * <p>
 *     Generates the source code of a builder for a given class.
 * </p>
 */
public interface FluentBuilderGeneratorService {

    /**
     * <p>
     *     Generates the source code of a builder for {@code clazz}.
     * </p>
     * @param clazz The class for which to generate a builder, usually a POJO. Must not be {@code null}.
     */
    void generateSourceCode(final Class<?> clazz);
}
