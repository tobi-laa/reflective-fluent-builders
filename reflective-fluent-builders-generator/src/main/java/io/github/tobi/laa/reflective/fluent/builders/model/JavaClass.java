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

    @lombok.NonNull
    private final Visibility visibility;

    private final boolean isAbstract;

    private final boolean isStatic;

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

    /**
     * <p>
     * Determines if this {@code JavaClass} object represents an interface type.
     * </p>
     *
     * @return {@code true} if this {@code JavaClass} object represents an interface; {@code false} otherwise.
     * @see Class#isInterface()
     */
    public boolean isInterface() {
        return clazz.isInterface();
    }

    /**
     * <p>
     * Returns {@code true} if the underlying class is an anonymous class.
     * </p>
     *
     * @return {@code true} if this class is an anonymous class.
     * @see Class#isAnonymousClass()
     */
    public boolean isAnonymousClass() {
        return clazz.isAnonymousClass();
    }

    /**
     * <p>
     * Returns true if this class was declared as an enum in the source code.
     * </p>
     *
     * @return true if this class was declared as an enum in the source code
     * @see Class#isEnum()
     */
    public boolean isEnum() {
        return clazz.isEnum();
    }

    /**
     * <p>
     * Determines if this {@code JavaClass} object represents a primitive type.
     * </p>
     *
     * @return true if this class represents a primitive type
     * @see Class#isPrimitive()
     */
    public boolean isPrimitive() {
        return clazz.isPrimitive();
    }

    /**
     * <p>
     * Returns {@code true} if the underlying class is a member class.
     * </p>
     *
     * @return {@code true} if this class is a member class.
     * @see Class#isMemberClass()
     */
    public boolean isMemberClass() {
        return clazz.isMemberClass();
    }
}
