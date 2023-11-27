package io.github.tobi.laa.reflective.fluent.builders.mojo;

import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import lombok.RequiredArgsConstructor;
import org.codehaus.plexus.logging.AbstractLogEnabled;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * Encapsulates the logic for deleting orphaned builders.
 * </p>
 *
 * @see MojoParams#isDeleteOrphanedBuilders()
 */
@Singleton
@Named
@RequiredArgsConstructor(onConstructor_ = @Inject)
class OrphanDeleter extends AbstractLogEnabled {

    @lombok.NonNull
    private final JavaFileHelper javaFileHelper;

    /**
     * <p>
     * Deletes all orphaned builders, i.e. builders that have no corresponding entry in {@code metadata}.
     * </p>
     *
     * @param target   The target directory in which builder files reside. Must not be {@code null}.
     * @param metadata The metadata of all builders that should be generated to compare against the content of
     *                 {@code target}. Must not be {@code null}.
     * @throws IOException If an I/O error occurs.
     */
    void deleteOrphanedBuilders(final Path target, final Set<BuilderMetadata> metadata) throws IOException {
        Objects.requireNonNull(target);
        Objects.requireNonNull(metadata);
        final var expectedJavaNames = metadata.stream()
                .map(m -> m.getPackageName() + '.' + m.getName())
                .collect(Collectors.toSet());
        Files.walkFileTree(target, new OrphanDeletingVisitor(target, expectedJavaNames));
    }

    @RequiredArgsConstructor
    private class OrphanDeletingVisitor extends SimpleFileVisitor<Path> {

        private final Path target;

        private final Set<String> expectedJavaNames;

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            final var pathToBuilder = target.relativize(file);
            final var builderJavaName = javaFileHelper.pathToJavaName(pathToBuilder);
            if (!expectedJavaNames.contains(builderJavaName)) {
                getLogger().info("Deleting orphaned builder file " + file);
                Files.delete(file);
            }
            return super.visitFile(file, attrs);
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            if (isEmptyDir(dir) && !dir.equals(target)) {
                getLogger().info("Deleting orphaned builder directory " + dir);
                Files.delete(dir);
            }
            return super.postVisitDirectory(dir, exc);
        }
    }

    private boolean isEmptyDir(final Path dir) throws IOException {
        try (final DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir)) {
            return !directoryStream.iterator().hasNext();
        }
    }
}