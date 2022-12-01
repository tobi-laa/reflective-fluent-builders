package com.github.tobi.laa.fluent.builder.maven.plugin.service.api;

import com.github.tobi.laa.fluent.builder.maven.plugin.model.Visibility;

/**
 * <p>
 *     Maps {@link java.lang.reflect.Modifier modifiers} to {@link com.github.tobi.laa.fluent.builder.maven.plugin.model.Visibility Visibility}.
 * </p>
 */
public interface VisibilityService {

    /**
     * <p>
     *     Maps {@code modifiers} to {@link com.github.tobi.laa.fluent.builder.maven.plugin.model.Visibility Visibility}.
     * </p>
     * @param modifiers The {@link java.lang.reflect.Modifier modifiers} of a method or field.
     * @return The visibility corresponding to {@code modifiers}, never {@code null}.
     */
    Visibility toVisibility(final int modifiers);
}
