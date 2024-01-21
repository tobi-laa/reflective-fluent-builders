package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;
import static java.util.Objects.compare;

/**
 * <p>
 * Basic implementation of {@link Setter}.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
public abstract class AbstractSetter implements Setter {

    @lombok.NonNull
    private final String methodName;

    @lombok.NonNull
    private final String propertyName;

    @lombok.NonNull
    private final Visibility visibility;

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
                            () -> compare(getPropertyType(), other.getPropertyType(), new SetterTypeComparator()), //
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
        final var aSetter = (Setter) anObject;
        return Objects.equals(getMethodName(), aSetter.getMethodName()) && //
                compare(getPropertyType(), aSetter.getPropertyType(), new SetterTypeComparator()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethodName(), getPropertyType());
    }
}
