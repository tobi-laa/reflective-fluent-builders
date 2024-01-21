package io.github.tobi.laa.reflective.fluent.builders.props.api;

import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import io.github.tobi.laa.reflective.fluent.builders.service.api.WriteAccessorService;

import java.util.Set;
import java.util.function.Predicate;

/**
 * <p>
 * Provides all properties or settings for the various services, components, generators.
 * </p>
 */
public interface BuildersProperties {

    /**
     * <p>
     * The package in which to place the generated builders.
     * </p>
     * <p>
     * Relative paths can be specified with the help of
     * {@link BuilderConstants#PACKAGE_PLACEHOLDER}.
     * As {@link BuilderConstants#PACKAGE_PLACEHOLDER}
     * is also the default value, builders will be placed within the same package as the classes built by them if
     * nothing else is specified.
     * </p>
     *
     * @return The package in which to place the generated builders.
     */
    String getBuilderPackage();

    /**
     * <p>
     * The suffix to append to builder classes. The default value is {@code Builder}, meaning a builder for a class
     * named {@code Person} would be named {@code PersonBuilder}.
     * </p>
     *
     * @return The suffix to append to builder classes.
     */
    String getBuilderSuffix();

    /**
     * <p>
     * The prefix used for identifying setter methods via reflection when analyzing classes.
     * The default value is {@code set}.
     * </p>
     *
     * @return The prefix used for identifying setter methods via reflection when analyzing classes.
     * @see WriteAccessorService
     */
    String getSetterPrefix();

    /**
     * <p>
     * The prefix used for identifying getter methods via reflection when analyzing classes.
     * The default value is {@code get}.
     * </p>
     *
     * @return The prefix used for identifying getter methods via reflection when analyzing classes.
     * @see WriteAccessorService
     */
    String getGetterPrefix();

    /**
     * <p>
     * If this is set to {@code true}, it is assumed that getters of collections without a corresponding setter will
     * lazily initialize the underlying collection. The generated builders will use a get-and-add paradigm where
     * necessary to construct a collection.
     * </p>
     *
     * @return Whether to support using a get-and-add paradigm in generated builders.
     * @see WriteAccessorService
     */
    boolean isGetAndAddEnabled();

    /**
     * <p>
     * If this is set to {@code true}, the generated builders will use direct field access if possible and if no setter
     * is available.
     * </p>
     *
     * @return Whether to support direct field access in generated builders.
     * @see WriteAccessorService
     */
    boolean isDirectFieldAccessEnabled();

    /**
     * <p>
     * Specifies classes to be excluded when generating builders.
     * </p>
     *
     * @return Predicates denoting classes to be excluded when generating builders. Never {@code null}.
     * @see BuilderMetadataService#filterOutConfiguredExcludes(Set)
     */
    Set<Predicate<Class<?>>> getExcludes();

    /**
     * <p>
     * Properties relating to hierarchy collection of classes.
     * </p>
     *
     * @return Properties relating to hierarchy collection of classes. Never {@code null}.
     */
    HierarchyCollection getHierarchyCollection();

    interface HierarchyCollection {

        /**
         * <p>
         * Specifies classes to be excluded from the hierarchy collection. They will not be added to the result.
         * Furthermore, if a class from {@code excludes} is encountered during ancestor traversal of the starting class
         * it is immediately stopped.
         * </p>
         *
         * @return Predicates denoting classes to be excluded from the hierarchy collection. Never {@code null}.
         * @see ClassService#collectFullClassHierarchy(io.github.classgraph.ClassInfo)
         */
        Set<Predicate<Class<?>>> getExcludes();
    }
}
