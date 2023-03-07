package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.Complex;
import com.github.tobi.laa.reflective.fluent.builders.test.models.full.Full;
import com.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.Jaxb;
import com.github.tobi.laa.reflective.fluent.builders.test.models.nested.Nested;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.Simple;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoDefaultConstructor;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Child;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Parent;
import com.github.tobi.laa.reflective.fluent.builders.test.models.visibility.Visibility;
import com.soebes.itf.jupiter.extension.MavenDebug;
import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenRepository;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import org.assertj.core.api.Assertions;

import java.nio.file.Paths;

import static com.github.tobi.laa.reflective.fluent.builders.mojo.IntegrationTestConstants.MAVEN_SHARED_LOCAL_CACHE;
import static com.github.tobi.laa.reflective.fluent.builders.mojo.TargetContainsExpectedBuildersCondition.expectedBuilder;
import static com.github.tobi.laa.reflective.fluent.builders.mojo.TargetContainsExpectedBuildersCondition.expectedBuilders;
import static com.github.tobi.laa.reflective.fluent.builders.mojo.TargetHasDirCondition.emptyDirInTarget;
import static com.github.tobi.laa.reflective.fluent.builders.mojo.TargetHasDirCondition.nonEmptyDirInTarget;
import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

@MavenJupiterExtension
@MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
class GenerateBuildersMojoIT {

    private final ProjectResultHelper projectResultHelper = new ProjectResultHelper();

    @MavenTest
    void testGenerationForPackageComplex(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(expectedBuilders(Complex.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageFull(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(expectedBuilders(Full.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageSimple(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(expectedBuilders(Simple.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageUnbuildable(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(emptyDirInTarget(Paths.get("generated-sources", "builders")));
    }

    @MavenTest
    void testGenerationForPackageVisibility(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(expectedBuilders(Visibility.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageNested(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(expectedBuilders(Nested.class.getPackage(), false));
    }

    @MavenTest
    void testGenerationForPackageJaxb(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(expectedBuilders(Jaxb.class.getPackage(), false));
    }

    @MavenTest
    @MavenDebug
    void testGenerationForPackageSimpleWithDebugLogging(final MavenExecutionResult result) {
        assertThat(result).isSuccessful();
        final var targetDirectory = projectResultHelper.getGeneratedSourcesDir(result.getMavenProjectResult()).resolve("builders");
        assertThat(result) //
                .out() //
                .info() //
                .contains( //
                        "Scan package " + Simple.class.getPackage().getName() + " recursively for classes.", //
                        "Found 5 classes for which to generate builders.", //
                        "Make sure target directory " + targetDirectory + " exists.", //
                        "Generate builder for class " + Child.class.getName(), //
                        "Generate builder for class " + SimpleClass.class.getName(), //
                        "Generate builder for class " + Parent.class.getName());
        assertThat(result) //
                .out() //
                .debug() //
                .contains( //
                        "Properties are: StandardBuildersProperties(builderPackage=<PACKAGE_NAME>, builderSuffix=Builder, setterPrefix=set, getterPrefix=get, getAndAddEnabled=false, hierarchyCollection=StandardBuildersProperties.StandardHierarchyCollection())", //
                        "Builders will be generated for the following classes:", //
                        "- " + SimpleClassNoSetPrefix.class.getName(), //
                        "- " + SimpleClassNoDefaultConstructor.class.getName(), //
                        "- " + Child.class.getName(), //
                        "- " + SimpleClass.class.getName(), //
                        "- " + Parent.class.getName(), //
                        "The following classes cannot be built:", //
                        "- " + Simple.class.getName(), //
                        "Builders for the following classes would be empty and will thus be skipped:", //
                        "- " + SimpleClassNoDefaultConstructor.class.getName(), //
                        "- " + SimpleClassNoSetPrefix.class.getName(), //
                        "The following classes have been configured to be excluded:", //
                        "Add " + targetDirectory + " as source folder.");
    }

    @MavenTest
    void testGenerationInvalidTargetDirectory(final MavenExecutionResult result) {
        assertThat(result).isFailure();
        final var pomXml = result.getMavenProjectResult().getTargetProjectDirectory() //
                .resolve("pom.xml") //
                .toAbsolutePath() //
                .toString();
        assertThat(result) //
                .out() //
                .info() //
                .contains( //
                        "Scan package does.not.matter recursively for classes.", //
                        "Found 0 classes for which to generate builders.", //
                        "Make sure target directory " + pomXml + " exists.");
        assertThat(result) //
                .out() //
                .error() //
                .anySatisfy(s -> Assertions.assertThat(s) //
                        .containsSubsequence( //
                                "Failed to execute goal com.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                "generate-builders (default) on project", //
                                "Could not create target directory", //
                                pomXml, //
                                "-> [Help 1]"));
    }

    @MavenTest
    void testGenerationForPackageSimpleNoAddCompileSourceRoot(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(emptyDirInTarget(Paths.get("classes")));
    }

    @MavenTest
    @MavenDebug
    void testGenerationForPackageSimplePhaseGenerateTestSources(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(emptyDirInTarget(Paths.get("classes"))) //
                .has(nonEmptyDirInTarget(Paths.get("test-classes"))) //
                .has(expectedBuilders(Simple.class.getPackage(), true));
    }

    @MavenTest
    void testGenerationBuilderFileCannotBeWritten(final MavenExecutionResult result) {
        assertThat(result).isFailure();
        final var srcMainJava = result.getMavenProjectResult().getTargetProjectDirectory() //
                .resolve("src").resolve("main").resolve("java") //
                .toAbsolutePath() //
                .toString();
        assertThat(result) //
                .out() //
                .info() //
                .contains( //
                        "Scan package com.github.tobi.laa.reflective.fluent.builders.test.models.simple recursively for classes.", //
                        "Found 5 classes for which to generate builders.", //
                        "Make sure target directory " + srcMainJava + " exists.");
        assertThat(result) //
                .out() //
                .error() //
                .anySatisfy(s -> Assertions.assertThat(s) //
                        .containsSubsequence( //
                                "Failed to execute goal com.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                "generate-builders (default) on project", //
                                "Could not create file for builder for " + SimpleClass.class.getName(), //
                                "SimpleClassBuilder.java: Is a directory -> [Help 1]"));
    }

    @MavenTest
    @MavenDebug
    void testGenerationForSimpleClassOnly(final MavenExecutionResult result) {
        assertThat(result) //
                .isSuccessful() //
                .project() //
                .hasTarget() //
                .has(expectedBuilder(SimpleClass.class.getName() + "Builder", false));
        assertThat(result) //
                .out() //
                .info() //
                .contains("Add class " + SimpleClass.class.getName() + '.');
    }

    @MavenTest
    void testGenerationClassNotFound(final MavenExecutionResult result) {
        assertThat(result) //
                .isFailure() //
                .out() //
                .error() //
                .anySatisfy(s -> Assertions.assertThat(s) //
                        .containsSubsequence( //
                                "Failed to execute goal com.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                "generate-builders (default) on project", //
                                ClassNotFoundException.class.getName(), //
                                "does.not.exist -> [Help 1]"));
    }
}
