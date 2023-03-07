package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * <p>
 * {@link java.nio.file.FileVisitor} that simply collects all
 * {@link java.nio.file.Files#isRegularFile(Path, LinkOption...) files} within an immutable list.
 * </p>
 */
class CollectingFileVisitor extends SimpleFileVisitor<Path> {

    private final ImmutableList.Builder<Path> files = ImmutableList.builder();

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        files.add(file);
        return super.visitFile(file, attrs);
    }

    public List<Path> getFiles() {
        return files.build();
    }
}
