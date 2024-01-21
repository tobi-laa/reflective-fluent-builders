package io.github.tobi.laa.reflective.fluent.builders.model;

import java.lang.reflect.Type;

/**
 * <p>
 * Represents the type of a property, so for instance the type of a field, the type of a parameter of a setter method or
 * the return type of a getter method.
 * </p>
 */
public interface PropertyType {

    /**
     * <p>
     * The full {@link Type} of this property, for instance {@code int.class}.
     * </p>
     *
     * @return The full {@link Type} of this property.
     */
    Type getType();
}
