package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.tobi.laa.reflective.fluent.builders.exception.ReflectionException;
import io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaClass;
import io.github.tobi.laa.reflective.fluent.builders.model.resource.OptionalResource;
import io.github.tobi.laa.reflective.fluent.builders.model.resource.Resource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * Offers methods for dealing with reflection related to classes and packages.
 * </p>
 */
public interface JavaClassService {

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
    List<JavaClass> collectFullClassHierarchy(final JavaClass clazz);

    /**
     * <p>
     * Collects all classes within the package {@code packageName} and all its sub-packages.
     * </p>
     *
     * @param packageName The package from which to start class collection. Must not be {@code null}.
     * @return All classes within the package {@code packageName} and all its sub-packages wrapped within a
     * {@link Resource}.
     * @throws ReflectionException If an error occurs while
     *                             accessing classes in
     *                             {@code packageName}.
     */
    Resource<Set<JavaClass>> collectClassesRecursively(final String packageName);

    /**
     * <p>
     * Attempts to load the class with the fully qualified {@code className} if it exists on the current classpath.
     * Returns an {@link Optional#empty() empty Optional} if no such class exists.
     * </p>
     *
     * @param className Fully qualified name of the class to attempt to load. Must not be {@code null}.
     * @return The class with the fully qualified {@code className} if it exists on the current classpath, otherwise an
     * {@link Optional#empty() empty Optional} wrapped within a {@link Resource}.
     * @throws ReflectionException If an error (<em>not</em> {@link ClassNotFoundException}) occurs while attempting to
     *                             load {@code className}.
     */
    OptionalResource<JavaClass> loadClass(final String className);
}
