package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaClass;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;

import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * Offers methods for identifying for which classes builder generation is possible as well as collecting
 * {@link BuilderMetadata metadata about a builder class}.
 * </p>
 */
public interface BuilderMetadataService {

    /**
     * <p>
     * Collects metadata necessary for generating a builder for {@code clazz}.
     * </p>
     *
     * @param clazz The class for which the metadata for generating a builder should be collected. Must not be
     *              {@code null}.
     * @return Metadata necessary for generating a builder for {@code clazz}.
     * @throws ReflectionException If an error occurs while accessing classes via reflection.
     */
    BuilderMetadata collectBuilderMetadata(final JavaClass clazz);

    /**
     * <p>
     * Filters out all classes from {@code classes} for which it is not possible to generate builders. These includes
     * the following:
     * </p>
     * <ul>
     *     <li>interfaces</li>
     *     <li>abstract classes</li>
     *     <li>package-info</li>
     *     <li>primitive classes</li>
     *     <li>member classes</li>
     *     <li>anonymous classes</li>
     *     <li>enums</li>
     *     <li>private and protected classes</li>
     *     <li>package private classes <em>if</em> builders should be placed in a different package than the class to
     *     be built</li>
     * </ul>
     *
     * @param classes The classes from which to filter out all classes for which it is not possible to generate
     *                builders. Must not be {@code null}.
     * @return {@code classes} but without all classes for which it is not possible to generate builders. Never
     * {@code null}.
     */
    Set<Class<?>> filterOutNonBuildableClasses(final Set<JavaClass> classes);

    /**
     * <p>
     * Filters out the configured excludes, i.e. the classes for which no builders should be generated.
     * </p>
     *
     * @param classes The classes from which to filter out all configured excludes. Must not be {@code null}.
     * @return {@code classes} but without all configured excludes. Never {@code null}.
     * @see BuildersProperties#getExcludes()
     */
    Set<Class<?>> filterOutConfiguredExcludes(final Set<JavaClass> classes);

    /**
     * <p>
     * Filters out all metadata for builders whose generation would yield an empty builder, i.e. those for which no
     * setters have been detected at all.
     * </p>
     *
     * @param builderMetadata The builder metadata from which to filter out all metadata for builders whose generation
     *                        would yield an empty builder. Must not be {@code null}.
     * @return {@code builderMetadata} but without all metadata for builders whose generation would yield an empty
     * builder. Never {@code null}.
     */
    Set<BuilderMetadata> filterOutEmptyBuilders(final Collection<BuilderMetadata> builderMetadata);
}
