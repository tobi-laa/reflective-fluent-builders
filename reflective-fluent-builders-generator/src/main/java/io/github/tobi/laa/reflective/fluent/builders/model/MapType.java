package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Type;

/**
 * <p>
 * Property type implementation for a Java {@link java.util.Map}.
 * </p>
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MapType implements PropertyType {

    @lombok.NonNull
    @EqualsAndHashCode.Include
    private final Type type;

    /**
     * <p>
     * The type of the key of the elements within the map, for instance {@code Integer.class} if {@code type} is
     * {@code Map<Integer, String>}.
     * </p>
     */
    @lombok.NonNull
    private final Type keyType;


    /**
     * <p>
     * The type of the value of the elements within the map, for instance {@code String.class} if {@code type} is
     * {@code Map<Integer, String>}.
     * </p>
     */
    @lombok.NonNull
    private final Type valueType;
}
