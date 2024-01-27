package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;

import java.lang.reflect.Type;

/**
 * <p>
 * Property type implementation for a Java {@link java.util.Collection}.
 * </p>
 */
@Data
public class CollectionType implements PropertyType {

    @lombok.NonNull
    private final Type type;

    /**
     * <p>
     * The type of the elements within the collection, for instance {@code Integer.class} if {@code type} is
     * {@code List<Integer>.class}.
     * </p>
     */
    @lombok.NonNull
    private final Type typeArg;
}
