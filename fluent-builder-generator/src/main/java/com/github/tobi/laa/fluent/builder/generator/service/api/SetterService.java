package com.github.tobi.laa.fluent.builder.generator.service.api;

import com.github.tobi.laa.fluent.builder.generator.model.Setter;

import java.util.Set;

/**
 * <p>
 * Gathers all setters found on a class.
 * </p>
 */
public interface SetterService {

    /**
     * <p>
     * Gathers all setters found on {@code clazz}.
     * </p>
     *
     * @param clazz The class for which to gather all setters. Must not be {@code null}.
     * @return All setters of {@code clazz}.
     */
    Set<Setter> gatherAllSetters(final Class<?> clazz);

    /**
     * <p>
     * Drop the configured setter prefix (for instance {@code set}) from {@code name}.
     * </p>
     *
     * @param name The (method) name from which to drop the configured setter prefix. Must not be {@code null}.
     * @return {@code name} with the configured setter prefix stripped from it. If {@code name} does not start with said
     * prefix or solely consists of it and would thus be empty after stripping it, {@code name} will be returned
     * unchanged.
     */
    String dropSetterPrefix(final String name);
}
