package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Type;

/**
 * <p>
 * Property type implementation for an array.
 * </p>
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArrayType implements PropertyType {

    @lombok.NonNull
    @EqualsAndHashCode.Include
    private final Type type;

    /**
     * <p>
     * The type of the elements within the array, for instance {@code int.class} if {@code type} is {@code int[].class}.
     * </p>
     */
    @lombok.NonNull
    private final Type componentType;
}
