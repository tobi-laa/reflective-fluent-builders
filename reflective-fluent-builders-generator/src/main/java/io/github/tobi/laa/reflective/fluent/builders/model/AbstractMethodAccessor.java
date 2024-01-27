package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * Common logic shared by {@link WriteAccessor} implementations that represent a method, i.e. setters, adders and
 * getters of collections and maps.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
@ToString(callSuper = true)
abstract class AbstractMethodAccessor extends AbstractWriteAccessor implements MethodAccessor {

    /**
     * <p>
     * The name of the method, for instance {@code setAge}.
     * </p>
     */
    @lombok.NonNull
    private final String methodName;

    /**
     * <p>
     * The types of exceptions that can be thrown by the method.
     * </p>
     * <p>
     * If no exceptions can be thrown, this set is empty.
     * </p>
     */
    @lombok.NonNull
    @Singular
    private final Set<Class<? extends Throwable>> exceptionTypes;

    @Override
    public boolean equals(final Object anObject) {
        if (this == anObject) {
            return true;
        } else if (anObject == null || anObject.getClass() != this.getClass()) {
            return false;
        }
        final AbstractMethodAccessor aSetter = (AbstractMethodAccessor) anObject;
        return Objects.equals(getMethodName(), aSetter.getMethodName()) && //
                getPropertyType().equals(aSetter.getPropertyType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethodName(), getPropertyType());
    }
}
