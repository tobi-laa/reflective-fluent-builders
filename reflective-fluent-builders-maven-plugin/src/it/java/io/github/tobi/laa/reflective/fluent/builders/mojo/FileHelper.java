package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.SneakyThrows;

import java.nio.file.FileSystems;
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
        return directory.resolve(javaNameToPath(className) + ".java");
    }

    List<Path> findJavaFiles(final Path directory, final Package pack) {
        return findFilesRecursively(directory.resolve(javaNameToPath(pack.getName())));
    }

    private String javaNameToPath(final String name) {
        return name.replace(".", FileSystems.getDefault().getSeparator());
    }
}
