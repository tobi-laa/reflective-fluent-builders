package com.github.tobi.laa.reflective.fluent.builders.props.api;

import com.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;

import java.util.Set;

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
     * Properties relating to hierarchy collection of classes.
     * </p>
     *
     * @return Properties relating to hierarchy collection of classes.
     */
    HierarchyCollection getHierarchyCollection();

    interface HierarchyCollection {

        /**
         * <p>
         * Classes to be excluded from the hierarchy collection. They will not be added to the result. Furthermore, if
         * a class from {@code excludes} is encountered during ancestor traversal of {@code clazz} it is immediately
         * stopped.
         * </p>
         *
         * @return Classes to be excluded from the hierarchy collection.
         * @see ClassService#collectFullClassHierarchy(Class)
         */
        Set<Class<?>> getClassesToExclude();
    }
}
