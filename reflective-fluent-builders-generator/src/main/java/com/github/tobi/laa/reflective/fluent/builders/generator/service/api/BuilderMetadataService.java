package com.github.tobi.laa.reflective.fluent.builders.generator.service.api;

import com.github.tobi.laa.reflective.fluent.builders.generator.model.BuilderMetadata;

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
     */
    BuilderMetadata collectBuilderMetadata(final Class<?> clazz);

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
    Set<Class<?>> filterOutNonBuildableClasses(final Set<Class<?>> classes);
}
