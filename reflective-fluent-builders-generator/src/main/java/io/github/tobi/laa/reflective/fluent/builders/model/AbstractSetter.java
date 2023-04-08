package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.function.Supplier;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractSetter implements Setter {

    @lombok.NonNull
    @EqualsAndHashCode.Include
    private final String methodName;

    @lombok.NonNull
    private final String paramName;

    @lombok.NonNull
    private final Visibility visibility;

    @Override
    public int compareTo(final Setter other) {
        Objects.requireNonNull(other);
        if (equals(other)) {
            return 0;
        } else {
            return Stream.<Supplier<Integer>>of( //
                            () -> compare(paramName, other.getParamName(), naturalOrder()), //
                            () -> compare(getParamType().getTypeName(), other.getParamType().getTypeName(), naturalOrder()))
                    .map(Supplier::get) //
                    .filter(i -> i != 0) //
                    .findFirst() //
                    .orElse(1);
        }
    }
}
