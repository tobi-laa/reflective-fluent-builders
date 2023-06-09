package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.Singular;

import java.nio.file.Path;
import java.util.Optional;
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
        private final Class<?> type;

        /**
         * <p>
         * Where the built type is located on the filesystem. Might point to a {@code class} or a {@code jar} file but
         * might also be {@code null}.
         * </p>
         */
        private final Path location;

        public Optional<Path> getLocation() {
            return Optional.ofNullable(location);
        }

        private final boolean accessibleNonArgsConstructor;

        @lombok.NonNull
        @Singular
        private final SortedSet<Setter> setters;
    }
}
