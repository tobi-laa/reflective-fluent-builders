package com.github.tobi.laa.fluent.builder.maven.plugin.service.api;

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
     * {@code clazz} as well as all interfaces implemented by {@code clazz} and all its ancestors.
     * </p>
     *
     * @param clazz    The class for which the full class hierarchy should be collected. Must not be {@code null}.
     * @param excludes Classes to be excluded from the hierarchy collection. They will not be added to the result.
     *                 Furthermore, if a class from {@code excludes} is encountered during ancestor traversal of
     *                 {@code clazz} it is immediately stopped.
     * @return The full class hierarchy of {@code clazz}. Never {@code null}.
     */
    Set<Class<?>> collectFullClassHierarchy(final Class<?> clazz, final Class<?>... excludes);

    /**
     * <p>
     * Collects all classes within the package {@code pack} and all its sub-packages.
     * </p>
     *
     * @param packageName The package from which to start class collection. Must not be {@code null}.
     * @return All classes within the package {@code pack} and all its sub-packages.
     * @throws com.github.tobi.laa.fluent.builder.maven.plugin.exception.ReflectionException If an error occurs while
     *                                                                                       accessing classes in
     *                                                                                       {@code packageName}.
     */
    Set<Class<?>> collectClassesRecursively(final String packageName);

}
