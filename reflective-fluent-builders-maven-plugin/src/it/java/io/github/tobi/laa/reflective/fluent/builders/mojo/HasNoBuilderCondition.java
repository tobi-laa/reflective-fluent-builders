package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.soebes.itf.jupiter.maven.MavenProjectResult;
import org.assertj.core.api.Condition;
import org.assertj.core.description.TextDescription;

import java.nio.file.Files;
import java.util.Objects;

/**
 * <p>
 * For checking that the builder for a class has <em>not</em> been generated.
 * </p>
 */
class HasNoBuilderCondition extends Condition<MavenProjectResult> {

    private final FileHelper fileHelper = new FileHelper();

    private final ProjectResultHelper projectResultHelper = new ProjectResultHelper();

    private final String builderClass;

    private HasNoBuilderCondition(final String builderClass) {
        this.builderClass = Objects.requireNonNull(builderClass);
    }

    @Override
    public boolean matches(final MavenProjectResult result) {
        final var buildersDir = projectResultHelper.getGeneratedSourcesDir(result).resolve("builders");
        final var builderFile = fileHelper.resolveJavaFile(buildersDir, builderClass);
        describedAs(new TextDescription("no builder %s in target base directory, but file exists", builderFile));
        return Files.notExists(builderFile);
    }

    static Condition<MavenProjectResult> noBuilder(final String builderClass) {
        return new HasNoBuilderCondition(builderClass);
    }
}
