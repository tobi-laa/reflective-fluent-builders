package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.With;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Method;

/**
 * <p>
 * Implementation of {@link JavaMethod} that holds no further information about what kind of method (e.g. a setter or
 * getter) is contained within.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@With
@Data
public class GenericJavaMethod implements JavaMethod {

    @lombok.NonNull
    private final Method method;

    @lombok.NonNull
    private final String methodName;

    @lombok.NonNull
    private final Visibility visibility;

    @lombok.NonNull
    private final JavaClass declaringClass;
}
