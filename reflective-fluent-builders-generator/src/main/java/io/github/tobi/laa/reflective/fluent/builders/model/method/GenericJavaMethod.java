package io.github.tobi.laa.reflective.fluent.builders.model.method;

import io.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaClass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * <p>
 * Implementation of {@link JavaMethod} that holds no further information about what kind of method (e.g. a setter or
 * getter) is contained within.
 * </p>
 */
@SuperBuilder
@Data
public class GenericJavaMethod implements JavaMethod {

    @lombok.NonNull
    @Getter(AccessLevel.PRIVATE)
    @ToString.Exclude
    private final Supplier<Method> methodSupplier;

    // inner class so field is not exposed in Lombok-generated builder
    @ToString.Exclude
    private final MethodWrapper methodWrapper = new MethodWrapper();

    @lombok.NonNull
    private final String methodName;

    @lombok.NonNull
    private final Visibility visibility;

    @lombok.NonNull
    private final JavaClass declaringClass;

    @Override
    public Method loadMethod() {
        if (methodWrapper.method == null) {
            methodWrapper.method = Objects.requireNonNull(methodSupplier.get());
        }
        return methodWrapper.method;
    }

    private static class MethodWrapper {
        private Method method;
    }
}
