package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

import static java.util.Objects.compare;

/**
 * <p>
 * A {@link WriteAccessor} that represents a field or the direct writing access to it.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
@ToString(callSuper = true)
public class FieldAccessor extends AbstractWriteAccessor {

    @lombok.NonNull
    private final boolean isFinal;

    @Override
    public boolean equals(final Object anObject) {
        if (this == anObject) {
            return true;
        } else if (anObject == null || anObject.getClass() != this.getClass()) {
            return false;
        }
        final var anAccessor = (FieldAccessor) anObject;
        return Objects.equals(getPropertyName(), anAccessor.getPropertyName()) && //
                compare(getPropertyType(), anAccessor.getPropertyType(), new ParamTypeComparator()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPropertyName(), getPropertyType());
    }

    /**
     * <p>
     * Creates a <em>new</em> {@link FieldAccessor} with all values kept the same except for {@code propertyName}.
     * </p>
     *
     * @param propertyName The new property name for the newly constructed {@link FieldAccessor}.
     * @return A new {@link FieldAccessor} with all values kept the same except for {@code propertyName}.
     */
    @Override
    public FieldAccessor withPropertyName(String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
