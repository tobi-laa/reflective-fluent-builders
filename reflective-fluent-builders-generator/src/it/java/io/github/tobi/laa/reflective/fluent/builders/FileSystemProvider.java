package io.github.tobi.laa.reflective.fluent.builders;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * <p>
 * Provides a {@link FileSystem} to be injected via DI.
 * </p>
 */
@Named
@Singleton
@SuppressWarnings("unused")
class FileSystemProvider implements Provider<FileSystem> {
    @Override
    public FileSystem get() {
        return FileSystems.getDefault();
    }
}
