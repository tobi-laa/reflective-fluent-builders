package io.github.tobi.laa.reflective.fluent.builders.service.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * <p>
 * Provides service methods for checking whether a class, type, method or constructor is accessible from a given
 * package.
 * </p>
 */
public interface AccessibilityService {

    /**
     * <p>
     * Checks whether {@code clazz} is accessible from the given {@code packageName}.
     * </p>
     *
     * @param clazz       The class for which to check whether it is accessible from the given package. Must not be
     *                    {@code null}.
     * @param packageName The name of the package for which to check whether {@code clazz} is accessible from it. Must
     *                    not be {@code null}.
     * @return {@code true} if {@code clazz} is accessible from {@code packageName}, {@code false} otherwise.
     */
    boolean isAccessibleFrom(final Class<?> clazz, final String packageName);

    /**
     * <p>
     * Checks whether {@code type} is accessible from the given {@code packageName}.
     * </p>
     *
     * @param type        The type for which to check whether it is accessible from the given package. Must not be
     *                    {@code null}.
     * @param packageName The name of the package for which to check whether {@code type} is accessible from it. Must
     *                    not be {@code null}.
     * @return {@code true} if {@code type} is accessible from {@code packageName}, {@code false} otherwise.
     */
    boolean isAccessibleFrom(final Type type, final String packageName);

    /**
     * <p>
     * Checks whether {@code method} is accessible from the given {@code packageName}.
     * </p>
     *
     * @param method      The method for which to check whether it is accessible from the given package. Must not be
     *                    {@code null}.
     * @param packageName The name of the package for which to check whether {@code method} is accessible from it. Must
     *                    not be {@code null}.
     * @return {@code true} if {@code method} is accessible from {@code packageName}, {@code false} otherwise.
     */
    boolean isAccessibleFrom(final Method method, final String packageName);

    /**
     * <p>
     * Checks whether {@code constructor} is accessible from the given {@code packageName}.
     * </p>
     *
     * @param constructor The constructor for which to check whether it is accessible from the given package. Must not
     *                    be {@code null}.
     * @param packageName The name of the package for which to check whether {@code constructor} is accessible from it.
     *                    Must not be {@code null}.
     * @return {@code true} if {@code constructor} is accessible from {@code packageName}, {@code false} otherwise.
     */
    boolean isAccessibleFrom(final Constructor<?> constructor, final String packageName);

    /**
     * <p>
     * Checks whether {@code field} is accessible from the given {@code packageName}.
     * </p>
     *
     * @param field       The field for which to check whether it is accessible from the given package. Must not be
     *                    {@code null}.
     * @param packageName The name of the package for which to check whether {@code field} is accessible from it. Must
     *                    not be {@code null}.
     * @return {@code true} if {@code field} is accessible from {@code packageName}, {@code false} otherwise.
     */
    boolean isAccessibleFrom(final Field field, final String packageName);
}
