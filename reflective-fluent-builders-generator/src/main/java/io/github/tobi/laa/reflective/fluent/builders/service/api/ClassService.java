package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
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
     * <p>
     * The elements in the returned list are unique.
     * </p>
     * <p>
     * The order of said list is that in which the elements were encountered when traversing the class hierarchy. That
     * means that {@code clazz} is always the first element and the top-most parent classes or interfaces are the
     * last ones.
     * </p>
     *
     * @param clazz The class for which the full class hierarchy should be collected. Must not be {@code null}.
     * @return The full class hierarchy of {@code clazz}. Never {@code null}.
     */
    List<Class<?>> collectFullClassHierarchy(final Class<?> clazz);

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

    /**
     * <p>
     * Determines the location of the file that contains the given {@code clazz}. If it stems from an external
     * dependency, this will point to a {@code jar} file. For local class files, it will point to the corresponding
     * {@code class} file.
     * </p>
     *
     * @param clazz The class for which to determine its location on the filesystem. Must not be {@code null.}
     * @return The location of the file that contains {@code clazz}, if it could be determined.
     */
    Optional<Path> determineClassLocation(final Class<?> clazz);

    /**
     * <p>
     * Attempts to load the class with the fully qualified {@code className} if it exists on the current classpath.
     * Returns an {@link Optional#empty() empty Optional} if no such class exists.
     * </p>
     *
     * @param className Fully qualified name of the class to attempt to load. Must not be {@code null}.
     * @return The class with the fully qualified {@code className} if it exists on the current classpath, otherwise an
     * {@link Optional#empty() empty Optional}.
     * @throws ReflectionException If an error (<em>not</em> {@link ClassNotFoundException}) occurs while attempting to
     *                             load {@code className}.
     */
    Optional<Class<?>> loadClass(final String className);
}
