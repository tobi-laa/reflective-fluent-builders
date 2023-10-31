package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.nio.file.Path;
import java.util.Optional;

/**
 * <p>
 * Represents a Java {@link Class class}. Contains the {@link Class} object as well as additional metadata.
 * </p>
 */
@Data
@Builder
@With
public class JavaClass {

    @lombok.NonNull
    private final Class<?> clazz;

    private final Path classLocation;

    private final Path sourceLocation;

    /**
     * <p>
     * Returns where the class is located on the filesystem. Might point to a {@code class} or a {@code jar} file but
     * might also be absent.
     * </p>
     *
     * @return Path pointing to where the class is located on the filesystem.
     */
    public Optional<Path> getClassLocation() {
        return Optional.ofNullable(classLocation);
    }

    /**
     * <p>
     * Where the source is located on the filesystem. Might point to a {@code java} or a {@code kt} file but might also
     * be absent.
     * </p>
     * <p>
     * Will always be a <em>relative</em> {@link Path path}, so the exact location can only be determined in combination
     * with a given root directory for source files.
     * </p>
     *
     * @return <em>Relative</em> path pointing to where the source is located on the filesystem.
     */
    public Optional<Path> getSourceLocation() {
        return Optional.ofNullable(classLocation);
    }
}
