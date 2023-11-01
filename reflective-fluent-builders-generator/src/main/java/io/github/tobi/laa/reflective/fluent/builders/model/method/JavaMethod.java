package io.github.tobi.laa.reflective.fluent.builders.model.method;

import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaClass;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * <p>
 * Represents a Java {@link java.lang.reflect.Method method}. Contains the {@link java.lang.reflect.Method} object as
 * well as additional metadata.
 * </p>
 */
public interface JavaMethod {

    /**
     * <p>
     * Loads the underlying {@link Method} using the {@link Supplier}.
     * </p>
     * <p>
     * Calling this method multiple times should <em>not</em> load the {@link Method} multiple times but instead return
     * the same instance.
     * </p>
     *
     * @return The underlying {@link Method}.
     * @throws NullPointerException If the {@link Supplier} returns {@code null}.
     */
    Method loadMethod();

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
    JavaClass getDeclaringClass();
}
