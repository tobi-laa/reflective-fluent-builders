package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;
import static java.util.Objects.compare;

/**
 * <p>
 * Common logic shared by most {@link WriteAccessor} implementations.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
abstract class AbstractWriteAccessor implements WriteAccessor {

    @lombok.NonNull
    private final PropertyType propertyType;

    @lombok.NonNull
    private final String propertyName;

    @lombok.NonNull
    private final Visibility visibility;

    @lombok.NonNull
    private final Class<?> declaringClass;

    @Override
    public int compareTo(@NonNull final WriteAccessor other) {
        Objects.requireNonNull(other);
        if (equals(other)) {
            return 0;
        } else {
            return Stream.<IntSupplier>of( //
                            () -> compare(propertyName, other.getPropertyName(), naturalOrder()), //
                            () -> compare(getPropertyType(), other.getPropertyType(), new ParamTypeComparator()), //
                            () -> compareClasses(getDeclaringClass(), other.getDeclaringClass()), //
                            () -> compare(getClass().getName(), other.getClass().getName(), naturalOrder()))
                    .map(IntSupplier::getAsInt) //
                    .filter(i -> i != 0) //
                    .findFirst() //
                    .orElse(1);
        }
    }

    private int compareClasses(final Class<?> clazz, final Class<?> anotherClazz) {
        if (clazz == anotherClazz) {
            return 0;
        } else if (clazz.isAssignableFrom(anotherClazz)) {
            return 1;
        } else {
            return -1;
        }
    }
}
