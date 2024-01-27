package io.github.tobi.laa.reflective.fluent.builders.model;

import java.util.Set;

/**
 * <p>
 * A {@link WriteAccessor} that represents a method, i.e. a setter, adder or getter of a collection or map.
 * </p>
 */
public interface MethodAccessor extends WriteAccessor {
    
    /**
     * <p>
     * The name of the method, for instance {@code setAge}.
     * </p>
     */
    String getMethodName();

    /**
     * <p>
     * The types of exceptions that can be thrown by the method.
     * </p>
     * <p>
     * If no exceptions can be thrown, this set is empty.
     * </p>
     */
    Set<Class<? extends Throwable>> getExceptionTypes();
}
