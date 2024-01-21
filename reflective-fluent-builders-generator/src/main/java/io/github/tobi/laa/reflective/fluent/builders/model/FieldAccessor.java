package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;
import static java.util.Objects.compare;

/**
 * <p>
 * A {@link WriteAccessor} that represents a field or the direct writing access to it.
 * </p>
 */
@Builder(toBuilder = true)
@Data
public class FieldAccessor implements WriteAccessor {

    @lombok.NonNull
    private final PropertyType propertyType;

    @lombok.NonNull
    private final String propertyName;

    @lombok.NonNull
    private final Visibility visibility;

    @lombok.NonNull
    private final boolean isFinal;

    @lombok.NonNull
    private final Class<?> declaringClass;

    @Override
    public int compareTo(final WriteAccessor other) {
        Objects.requireNonNull(other);
        if (equals(other)) {
            return 0;
        } else {
            return Stream.<IntSupplier>of( //
                            () -> compare(propertyName, other.getPropertyName(), naturalOrder()), //
                            () -> compare(getPropertyType(), other.getPropertyType(), new ParamTypeComparator()), //
                            () -> compare(getClass().getName(), other.getClass().getName(), naturalOrder()))
                    .map(IntSupplier::getAsInt) //
                    .filter(i -> i != 0) //
                    .findFirst() //
                    .orElse(1);
        }
    }

    @Override
    public boolean equals(final Object anObject) {
        if (this == anObject) {
            return true;
        } else if (anObject == null || anObject.getClass() != this.getClass()) {
            return false;
        }
        final var aSetter = (FieldAccessor) anObject;
        return Objects.equals(getPropertyName(), aSetter.getPropertyName()) && //
                compare(getPropertyType(), aSetter.getPropertyType(), new ParamTypeComparator()) == 0;
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
     * @return A new {@link FieldAccessor} with all values kept the same except for {@code paramName}.
     */
    @Override
    public FieldAccessor withPropertyName(String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
