package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.classgraph.ClassInfo;
import lombok.Data;
import lombok.Singular;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        private final ClassInfo type;

        private final Path location;

        private final Path sourceFile;

        /**
         * <p>
         * Where the built type is located on the filesystem. Might point to a {@code class} or a {@code jar} file but
         * might also be absent.
         * </p>
         *
         * @return The location of the built type or {@link Optional#empty()} if the location is unknown.
         */
        public Optional<Path> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * <p>
         * The <em>name</em> of the source file of the built type such as {@code BuilderMetadata.class} or
         * {@code INeedToTry.kt}. Might be {@code null}.
         * </p>
         *
         * @return The name of the source file of the built type or {@link Optional#empty()} if the name is unknown.
         */
        public Optional<Path> getSourceFile() {
            return Optional.of(type).map(ClassInfo::getSourceFile).map(Paths::get);
        }

        private final boolean accessibleNonArgsConstructor;

        @lombok.NonNull
        @Singular
        private final SortedSet<WriteAccessor> writeAccessors;
    }
}
