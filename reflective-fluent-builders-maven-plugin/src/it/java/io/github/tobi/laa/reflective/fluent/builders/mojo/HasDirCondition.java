package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.base.Predicates;
import com.soebes.itf.jupiter.maven.MavenProjectResult;
import org.assertj.core.api.Condition;
import org.assertj.core.description.TextDescription;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * <p>
 * For performing assertions on project target dir that are easier to read.
 * </p>
 */
class HasDirCondition extends Condition<MavenProjectResult> {

    private final FileHelper fileHelper = new FileHelper();

    private final Path dir;

    private final Predicate<List<Path>> predicate;

    private HasDirCondition( //
                             final Path dir, //
                             final Predicate<List<Path>> predicate, //
                             final String description, //
                             final Object... args) {
        super(new TextDescription(description, args));
        this.dir = Objects.requireNonNull(dir);
        this.predicate = Objects.requireNonNull(predicate);
    }

    public boolean matches(final MavenProjectResult result) {
        final Path dirInTarget = result.getTargetProjectDirectory().resolve("target").resolve(dir);
        return predicate.test(fileHelper.findFilesRecursively(dirInTarget));
    }

    static Condition<MavenProjectResult> emptyDirInTarget(final Path dir) {
        return new HasDirCondition(
                dir,
                List::isEmpty,
                "empty subdirectory %s in target base directory",
                dir);
    }

    static Condition<MavenProjectResult> nonEmptyDirInTarget(final Path dir) {
        return new HasDirCondition(
                dir,
                Predicates.not(List::isEmpty),
                "non-empty subdirectory %s in target base directory",
                dir);
    }
}
