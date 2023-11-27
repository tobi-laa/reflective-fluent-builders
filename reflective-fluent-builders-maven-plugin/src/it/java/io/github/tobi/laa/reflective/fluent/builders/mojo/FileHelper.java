package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Convenience methods for {@link java.nio.file.Path}.
 * </p>
 */
class FileHelper {

    private final JavaFileHelper javaFileHelper;

    FileHelper() {
        this.javaFileHelper = new JavaFileHelper();
    }

    @SneakyThrows
    List<Path> findFilesRecursively(final Path directory) {
        if (Files.notExists(directory)) {
            return Collections.emptyList();
        } else {
            final CollectingFileVisitor visitor = new CollectingFileVisitor();
            Files.walkFileTree(directory, visitor);
            return visitor.getFiles();
        }
    }

    Path resolveJavaFile(final Path directory, final String className) {
        return directory.resolve(javaFileHelper.javaNameToPath(className) + ".java");
    }

    List<Path> findJavaFiles(final Path directory, final Package pack) {
        return findFilesRecursively(directory.resolve(javaFileHelper.javaNameToPath(pack.getName())));
    }
}
