package com.github.tobi.laa.reflective.fluent.builders.mojo;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * <p>
 * {@link java.nio.file.FileVisitor} that deletes all files and directories it traverses..
 * </p>
 */
class DeletingFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
        final FileVisitResult result = super.postVisitDirectory(dir, exc);
        Files.delete(dir);
        return result;
    }
}