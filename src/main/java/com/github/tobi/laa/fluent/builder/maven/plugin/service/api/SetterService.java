package com.github.tobi.laa.fluent.builder.maven.plugin.service.api;

import com.github.tobi.laa.fluent.builder.maven.plugin.model.Setter;

import java.util.Set;

/**
 * <p>
 *     Gathers all setters found on a class.
 * </p>
 */
public interface SetterService {

    /**
     * <p>
     *     Gathers all setters found on {@code clazz}.
     * </p>
     * @param clazz The class for which to gather all setters. Must not be {@code null}.
     * @return All setters of {@code clazz}.
     */
    Set<Setter> gatherAllSetters(final Class<?> clazz);
}
