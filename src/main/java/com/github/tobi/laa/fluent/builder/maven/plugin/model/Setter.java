package com.github.tobi.laa.fluent.builder.maven.plugin.model;

/**
 * <p>
 *     Represents a single setter of a class for which a builder is going to be generated.
 * </p>
 */
public interface Setter {

    /**
     * <p>
     *     The name of the setter method, for instance {@code setAge}.
     * </p>
     * @return The name of the method.
     */
    String getMethodName();

    /**
     * <p>
     *     The type of the setter method's single parameter, for instance {@code int.class}.
     * </p>
     * @return The type of the setter method's single parameter.
     */
    Class<?> getParamType();

    /**
     * <p>
     *     The visibility of the setter method, for instance {@code PUBLIC}.
     * </p>
     * @return The visibility of the setter method.
     */
    Visibility getVisibility();
}
