package io.github.tobi.laa.reflective.fluent.builders.mojo;

import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.maven.plugins.annotations.Parameter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * Encapsulates most {@link Parameter parameters} of
 * {@link io.github.tobi.laa.reflective.fluent.builders.mojo.GenerateBuildersMojo} while implementing
 * {@link BuildersProperties}.
 * </p>
 */
@Singleton
@Named
@Data
@ToString(doNotUseGetters = true)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MojoParams implements BuildersProperties {

    private String builderPackage;

    private String builderSuffix;

    private String setterPrefix;

    private String getterPrefix;

    private boolean getAndAddEnabled;

    @Valid
    private MojoParams.HierarchyCollection hierarchyCollection = new HierarchyCollection();

    @NotEmpty(message = "At least one <include> has to be specified.")
    @Valid
    private Set<Include> includes;

    @Valid
    private Set<Exclude> excludes;

    private File target;

    private boolean addCompileSourceRoot;

    private boolean deleteOrphanedBuilders;

    @lombok.NonNull
    @ToString.Exclude
    private final MavenBuild mavenBuild;

    public Set<Predicate<Class<?>>> getExcludes() {
        if (excludes == null) {
            return Set.of( //
                    clazz -> clazz.getSimpleName().endsWith("Builder"), //
                    clazz -> clazz.getSimpleName().endsWith("BuilderImpl"));
        } else {
            return excludes.stream().map(Exclude::toPredicate).collect(Collectors.toSet());
        }
    }

    public File getTarget() {
        if (target != null) {
            return target;
        } else if (mavenBuild.isTestPhase()) {
            return Paths.get(mavenBuild.getDirectory()) //
                    .resolve("generated-test-sources") //
                    .resolve("builders") //
                    .toFile();
        } else {
            return Paths.get(mavenBuild.getDirectory()) //
                    .resolve("generated-sources") //
                    .resolve("builders") //
                    .toFile();
        }
    }

    @Data
    @ToString(doNotUseGetters = true)
    public static class HierarchyCollection implements BuildersProperties.HierarchyCollection {

        @Valid
        private Set<Exclude> excludes;

        @Override
        public Set<Predicate<Class<?>>> getExcludes() {
            if (excludes == null) {
                return Set.of(Object.class::equals);
            } else {
                return excludes.stream().map(Exclude::toPredicate).collect(Collectors.toSet());
            }
        }
    }
}
