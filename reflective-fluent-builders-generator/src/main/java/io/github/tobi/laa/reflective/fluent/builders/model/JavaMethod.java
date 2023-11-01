package io.github.tobi.laa.reflective.fluent.builders.model;

import java.lang.reflect.Method;

/**
 * <p>
 * Represents a Java {@link java.lang.reflect.Method method}. Contains the {@link java.lang.reflect.Method} object as
 * well as additional metadata.
 * </p>
 */
public interface JavaMethod {

    /**
     * <p>
     * The underlying {@link Method}.
     * </p>
     *
     * @return The underlying method.
     */
    Method getMethod();

    /**
     * <p>
     * The name of the method, for instance {@code setAge}.
     * </p>
     *
     * @return The name of the method.
     */
    String getMethodName();

    /**
     * <p>
     * The visibility of the method, for instance {@code PUBLIC}.
     * </p>
     *
     * @return The visibility of the method.
     */
    Visibility getVisibility();

    /**
     * <p>
     * The class within which this method is defined. This is particularly important for methods inherited from super
     * classes or interfaces.
     * </p>
     *
     * @return The class within which this method is defined.
     */
    Class<?> getDeclaringClass();
}
