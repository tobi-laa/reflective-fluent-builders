package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.*;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * <p>
 * Represents a Java {@link Class class}. Contains the {@link Class} object as well as additional metadata.
 * </p>
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@With
@RequiredArgsConstructor
public class JavaClass {

    @lombok.NonNull
    @EqualsAndHashCode.Include
    private final String name;

    @lombok.NonNull
    private final String simpleName;

    @lombok.NonNull
    @Getter(AccessLevel.PRIVATE)
    private final Supplier<Class<?>> classSupplier;

    // inner class so field is not exposed in Lombok-generated builder
    private final ClassWrapper classWrapper = new ClassWrapper();

    @lombok.NonNull
    private final Visibility visibility;

    @lombok.NonNull
    private final JavaType type;

    private final boolean isStatic;

    private final boolean innerClass;

    private final boolean outerClass;

    private final Path classLocation;

    private final Path sourceLocation;

    private final JavaClass superclass;

    @lombok.NonNull
    @Singular("addInterface")
    private final Set<JavaClass> interfaces;

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

    /**
     * <p>
     * Returns the {@code JavaClass} representing the direct superclass of this {@code JavaClass}.
     * </p>
     *
     * @return The direct superclass of the class represented by this {@code JavaClass} object.
     */
    public Optional<JavaClass> getSuperclass() {
        return Optional.ofNullable(superclass);
    }

    /**
     * <p>
     * Loads the underlying class using the {@link Supplier}.
     * </p>
     * <p>
     * Calling this method multiple times will <em>not</em> load the class multiple times but instead return the same
     * instance.
     * </p>
     *
     * @return The underlying class.
     * @throws NullPointerException If the {@link Supplier} returns {@code null}.
     */
    public Class<?> loadClass() {
        if (classWrapper.clazz == null) {
            classWrapper.clazz = Objects.requireNonNull(classSupplier.get());
        }
        return classWrapper.clazz;
    }

    private static class ClassWrapper {
        private Class<?> clazz;
    }
}
