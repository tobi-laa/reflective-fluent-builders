package com.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Type;

/**
 * <p>
 * The setter of an array.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArraySetter extends AbstractSetter {

    /**
     * <p>
     * The type of the elements within the array being set by the setter method's single parameter, for instance
     * {@code int.class}.
     * </p>
     */
    @lombok.NonNull
    private final Type paramComponentType;

    @Override
    public ArraySetter withParamName(final String paramName) {
        return toBuilder().paramName(paramName).build();
    }
}
