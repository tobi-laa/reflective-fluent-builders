package io.github.tobi.laa.reflective.fluent.builders.model;

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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class ArraySetter extends AbstractSetter {

    @lombok.NonNull
    private final Type propertyType;

    /**
     * <p>
     * The type of the elements within the array being set by the setter method's single parameter, for instance
     * {@code int.class}.
     * </p>
     */
    @lombok.NonNull
    private final Type paramComponentType;

    @Override
    public ArraySetter withPropertyName(final String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
