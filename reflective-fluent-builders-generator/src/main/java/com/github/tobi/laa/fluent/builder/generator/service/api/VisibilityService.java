package com.github.tobi.laa.fluent.builder.generator.service.api;

import com.github.tobi.laa.fluent.builder.generator.model.Visibility;

/**
 * <p>
 * Maps {@link java.lang.reflect.Modifier modifiers} to {@link Visibility Visibility}.
 * </p>
 */
public interface VisibilityService {

    /**
     * <p>
     * Maps {@code modifiers} to {@link Visibility Visibility}.
     * </p>
     *
     * @param modifiers The {@link java.lang.reflect.Modifier modifiers} of a method or field.
     * @return The visibility corresponding to {@code modifiers}, never {@code null}.
     */
    Visibility toVisibility(final int modifiers);
}
