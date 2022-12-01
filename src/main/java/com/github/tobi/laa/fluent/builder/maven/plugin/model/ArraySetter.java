package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *     The setter of an array.
 * </p>
 */
@SuperBuilder
@Data
public class ArraySetter extends AbstractSetter {

    /**
     * <p>
     *     The type of the elements within the array being set by the setter method's single parameter, for instance
     *     {@code int.class}.
     * </p>
     */
    @lombok.NonNull
    private final Class<?> paramComponentType;
}
