package com.github.tobi.laa.reflective.fluent.builders.service.api;

import com.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;

import java.util.Set;

/**
 * <p>
 * Offers methods for dealing with reflection related to classes and packages.
 * </p>
 */
public interface ClassService {

    /**
     * <p>
     * Collects the full class hierarchy of {@code clazz}. That includes {@code clazz} itself, all super classes of
     * {@code clazz} as well as all interfaces implemented by {@code clazz} and all its ancestors. Implementations may
     * offer the possibility to exclude some classes.
     * </p>
     *
     * @param clazz The class for which the full class hierarchy should be collected. Must not be {@code null}.
     * @return The full class hierarchy of {@code clazz}. Never {@code null}.
     */
    Set<Class<?>> collectFullClassHierarchy(final Class<?> clazz);

    /**
     * <p>
     * Collects all classes within the package {@code pack} and all its sub-packages.
     * </p>
     *
     * @param packageName The package from which to start class collection. Must not be {@code null}.
     * @return All classes within the package {@code pack} and all its sub-packages.
     * @throws ReflectionException If an error occurs while
     *                             accessing classes in
     *                             {@code packageName}.
     */
    Set<Class<?>> collectClassesRecursively(final String packageName);

}
