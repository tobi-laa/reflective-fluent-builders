package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Type;

/**
 * <p>
 * The setter of a {@link java.util.Collection collection}, so for instance a list or a set.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class CollectionSetter extends AbstractSetter {

    @lombok.NonNull
    private final Type propertyType;

    /**
     * <p>
     * The type of the elements within the collection being set by the setter method's single parameter, for
     * instance {@code Integer.class}.
     * </p>
     */
    @lombok.NonNull
    private final Type paramTypeArg;

    @Override
    public CollectionSetter withPropertyName(final String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
