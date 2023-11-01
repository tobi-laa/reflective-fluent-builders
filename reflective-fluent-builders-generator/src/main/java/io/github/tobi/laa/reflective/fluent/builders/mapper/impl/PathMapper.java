package io.github.tobi.laa.reflective.fluent.builders.mapper.impl;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Objects;

/**
 * <p>
 * Helper for MapStruct-generated {@link org.mapstruct.Mapper mappers} for mapping to and from {@link Path}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class PathMapper {

    @lombok.NonNull
    private final FileSystem fileSystem;

    /**
     * <p>
     * Converts a {@link String} to a {@link Path}.
     * </p>
     *
     * @param path The string to convert to a {@link Path}. Must not be {@code null}.
     * @return The {@link Path} represented by {@code path}.
     */
    Path fromString(final String path) {
        Objects.requireNonNull(path);
        return fileSystem.getPath(path);
    }

    /**
     * <p>
     * Converts a {@link File} to a {@link Path}.
     * </p>
     *
     * @param path The {@link File} to convert to a {@link Path}. Must not be {@code null}.
     * @return The {@link Path} represented by {@code path}.
     */
    Path fromFile(final File path) {
        Objects.requireNonNull(path);
        return fileSystem.getPath(path.getPath());
    }
}
