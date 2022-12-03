package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *     Basic implementation of {@link Setter}.
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
    @EqualsAndHashCode.Include
    private final Class<?> paramType;

    @lombok.NonNull
    private final String paramName;

    @lombok.NonNull
    private final Visibility visibility;
}
