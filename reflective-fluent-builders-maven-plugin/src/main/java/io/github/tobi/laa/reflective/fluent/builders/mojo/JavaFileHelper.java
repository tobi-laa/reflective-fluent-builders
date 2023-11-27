package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.collect.Streams;

import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.io.Files.getNameWithoutExtension;

/**
 * <p>
 * Provides convenience methods for converting between Java class or Java package names and file paths.
 * </p>
 */
@Singleton
@Named
class JavaFileHelper {

    /**
     * <p>
     * Converts a Java class to a file path.
     * </p>
     * <p>
     * Example: {@code java.lang.String} becomes {@code java/lang/String.class}.
     * </p>
     *
     * @param clazz The class to convert. Must not be {@code null}.
     * @return The file path.
     */
    Path classToPath(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        final var className = clazz.getName().replace(clazz.getPackageName() + ".", "");
        return javaNameToPath(clazz.getPackageName()).resolve(className + ".class");
    }

    /**
     * <p>
     * Converts a Java package to a file path.
     * </p>
     * <p>
     * Example: {@code java.lang} becomes {@code java/lang}.
     * </p>
     *
     * @param pack The package to convert. Must not be {@code null}.
     * @return The file path.
     */
    Path packageToPath(final Package pack) {
        Objects.requireNonNull(pack);
        return javaNameToPath(pack.getName());
    }

    /**
     * <p>
     * Converts a Java class or package name to a file path.
     * </p>
     * <p>
     * Does not include the file extension if not specified.
     * </p>
     * <p>
     * Example: {@code "java.lang.String"} becomes {@code java/lang/String}.
     * </p>
     *
     * @param name The class or package name to convert. Must not be {@code null}.
     * @return The file path.
     */
    Path javaNameToPath(final String name) {
        Objects.requireNonNull(name);
        Path file = Paths.get("");
        for (final String subdir : name.split("\\.")) {
            file = file.resolve(subdir);
        }
        return file;
    }

    /**
     * <p>
     * Converts a file path to a Java class or package name.
     * </p>
     * <p>
     * Drops the file extension if present.
     * </p>
     * <p>
     * Example: {@code java/lang/String.class} becomes {@code java.lang.String}.
     * </p>
     *
     * @param path The file path to convert. Must not be {@code null}.
     * @return The class or package name.
     */
    String pathToJavaName(final Path path) {
        Objects.requireNonNull(path);
        final var pathNoExtension = path.resolveSibling(getNameWithoutExtension(path.getFileName().toString()));
        return Streams.stream(pathNoExtension).map(Path::toString).collect(Collectors.joining("."));
    }
}
