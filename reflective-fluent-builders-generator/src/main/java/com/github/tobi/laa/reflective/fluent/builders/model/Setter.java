package com.github.tobi.laa.reflective.fluent.builders.model;

import java.lang.reflect.Type;

/**
 * <p>
 * Represents a single setter of a class for which a builder is going to be generated.
 * </p>
 */
public interface Setter {

    /**
     * <p>
     * The name of the setter method, for instance {@code setAge}.
     * </p>
     *
     * @return The name of the method.
     */
    String getMethodName();

    /**
     * <p>
     * The type of the setter method's single parameter, for instance {@code int.class}.
     * </p>
     *
     * @return The type of the setter method's single parameter.
     */
    Type getParamType();

    /**
     * <p>
     * The name of the setter method's single parameter, for instance {@code age}.
     * </p>
     *
     * @return The name of the setter method's single parameter.
     */
    String getParamName();

    /**
     * <p>
     * The visibility of the setter method, for instance {@code PUBLIC}.
     * </p>
     *
     * @return The visibility of the setter method.
     */
    Visibility getVisibility();

    /**
     * <p>
     * Creates a <em>new</em> {@link Setter} with all values kept the same except for {@code paramName}.
     * </p>
     *
     * @param paramName The new param name for the newly constructed Setter.
     * @return A new {@link Setter} with all values kept the same except for {@code paramName}.
     */
    Setter withParamName(final String paramName);
}
