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
 * Common logic shared by {@link WriteAccessor} implementations that represent a method, i.e. setters, adders and
 * getters of collections and maps.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
abstract class AbstractMethodAccessor implements WriteAccessor {

    @lombok.NonNull
    private final PropertyType propertyType;

    @lombok.NonNull
    private final String propertyName;

    @lombok.NonNull
    private final Visibility visibility;

    @lombok.NonNull
    private final Class<?> declaringClass;

    /**
     * <p>
     * The name of the method, for instance {@code setAge}.
     * </p>
     */
    @lombok.NonNull
    private final String methodName;

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
        final var aSetter = (AbstractMethodAccessor) anObject;
        return Objects.equals(getMethodName(), aSetter.getMethodName()) && //
                compare(getPropertyType(), aSetter.getPropertyType(), new ParamTypeComparator()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethodName(), getPropertyType());
    }
}
