package com.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.Singular;

import java.util.Set;

/**
 * <p>
 * Holds metadata about a builder class (as well the class the builder builds).
 * </p>
 */
@lombok.Builder
@Data
public class BuilderMetadata {

    @lombok.NonNull
    private final String packageName;

    @lombok.NonNull
    private final String name;

    @lombok.NonNull
    private final BuiltType builtType;

    @lombok.Builder
    @Data
    public static class BuiltType {

        @lombok.NonNull
        private final Class<?> type;

        private final boolean accessibleNonArgsConstructor;

        @lombok.NonNull
        @Singular
        private final Set<Setter> setters;
    }
}
