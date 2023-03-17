package com.github.tobi.laa.reflective.fluent.builders.props.api;

import com.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;

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
     * The package in which to place the generated builders. Relative paths can be specified with the help of
     * {@link com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants#PACKAGE_PLACEHOLDER}.
     * </p>
     *
     * @return The package in which to place the generated builders.
     */
    String getBuilderPackage();

    /**
     * <p>
     * The suffix of the generated builders. If the suffix is set to {@code Builder}, a builder class for
     * {@code MyClass} would thus be named {@code MyClassBuilder}.
     * </p>
     *
     * @return The suffix of the generated builders.
     */
    String getBuilderSuffix();

    /**
     * <p>
     * The prefix of setters to use when analyzing classes.
     * </p>
     *
     * @return The prefix of setters to use when analyzing classes.
     * @see SetterService
     */
    String getSetterPrefix();

    /**
     * <p>
     * The prefix of getters to use when analyzing classes.
     * </p>
     *
     * @return The prefix of getters to use when analyzing classes.
     * @see SetterService
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
     * @see SetterService
     */
    boolean isGetAndAddEnabled();

    /**
     * <p>
     * Specifies classes to be excluded when generating builders.
     * </p>
     *
     * @return Predicates denoting classes to be excluded when generating builders. Never {@code null}.
     * @see com.github.tobi.laa.reflective.fluent.builders.service.api.BuilderMetadataService#filterOutConfiguredExcludes(Set)
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
         * @see ClassService#collectFullClassHierarchy(Class)
         */
        Set<Predicate<Class<?>>> getExcludes();
    }
}
