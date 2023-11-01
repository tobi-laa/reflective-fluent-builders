package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.model.javaclass.JavaClass;
import io.github.tobi.laa.reflective.fluent.builders.model.method.Setter;
import lombok.Data;
import lombok.Singular;

import java.util.SortedSet;

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
        private final JavaClass type;

        private final boolean accessibleNonArgsConstructor;

        @lombok.NonNull
        @Singular
        private final SortedSet<Setter> setters;
    }
}
