package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.soebes.itf.jupiter.extension.*;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.Complex;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.ClassWithHierarchy;
import io.github.tobi.laa.reflective.fluent.builders.test.models.full.Full;
import io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.Jaxb;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.NestedMarker;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.Simple;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoDefaultConstructor;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Child;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.hierarchy.Parent;
import io.github.tobi.laa.reflective.fluent.builders.test.models.visibility.Visibility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;

import java.nio.file.Paths;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;
import static io.github.tobi.laa.reflective.fluent.builders.mojo.IntegrationTestConstants.MAVEN_SHARED_LOCAL_CACHE;

@MavenJupiterExtension
class GenerateBuildersMojoIT {

    private final ProjectResultHelper projectResultHelper = new ProjectResultHelper();

    @Nested
    @MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
    class WithDefaultConfig {

        @MavenTest
        void packageComplex(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Complex.class.getPackage(), false));
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        void packageFull(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Full.class.getPackage(), false));
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        void packageSimple(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Simple.class.getPackage(), false));
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        void packageUnbuildable(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(HasDirCondition.emptyDirInTarget(Paths.get("generated-sources", "builders")));
            assertThat(result).out().warn().containsExactly("JAR will be empty - no content was marked for inclusion!");
        }

        @MavenTest
        void packageVisibility(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Visibility.class.getPackage(), false));
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        void packageNested(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(NestedMarker.class.getPackage(), false));
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        void packageJaxb(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Jaxb.class.getPackage(), false));
            assertThat(result).out().warn().isEmpty();
        }
    }

    @Nested
    @MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
    class WithDebugLogging {

        @MavenTest
        @MavenDebug
        void packageSimpleWithDebugLogging(final MavenExecutionResult result) {
            assertThat(result).isSuccessful();
            final var targetDirectory = projectResultHelper.getGeneratedSourcesDir(result.getMavenProjectResult()).resolve("builders");
            final var outputDirectory = projectResultHelper.getOutputDirectory(result.getMavenProjectResult());
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
            final var reflectiveFluentBuildersTestModels = projectResultHelper.resolveMavenArtifact(result.getMavenProjectResult(), //
                    "io.github.tobi-laa", //
                    "reflective-fluent-builders-test-models", //
                    System.getProperty("project.version"));
            final var jakartaXmlBindApi = projectResultHelper.resolveMavenArtifact(result.getMavenProjectResult(), //
                    "jakarta.xml.bind", //
                    "jakarta.xml.bind-api", //
                    System.getProperty("jakarta.xml.bind-api.version"));
            assertThat(result) //
                    .out() //
                    .debug() //
                    .contains( //
                            "Parameters are: MojoParams(builderPackage=<PACKAGE_NAME>, " +
                                    "builderSuffix=Builder, " +
                                    "setterPrefix=set, " +
                                    "getterPrefix=get, " +
                                    "getAndAddEnabled=false, " +
                                    "hierarchyCollection=MojoParams.HierarchyCollection(excludes=null), " +
                                    "includes=[Include(super=AbstractIncludeExclude(packageName=io.github.tobi.laa.reflective.fluent.builders.test.models.simple, className=null))], " +
                                    "excludes=null, " +
                                    "target=" + targetDirectory + ", " +
                                    "addCompileSourceRoot=true)", //
                            "Attempt to add " + outputDirectory + " to ClassLoader.", //
                            "Attempt to add " + reflectiveFluentBuildersTestModels + " to ClassLoader.", //
                            "Attempt to add " + jakartaXmlBindApi + " to ClassLoader.", //
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
            assertThat(result).out().warn().isEmpty();
        }
    }

    @Nested
    @MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
    class WithCustomValidConfig {

        @MavenTest
        void packageSimpleNoAddCompileSourceRoot(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(HasDirCondition.emptyDirInTarget(Paths.get("classes")));
            assertThat(result).out().warn().containsExactly("JAR will be empty - no content was marked for inclusion!");
        }

        @MavenTest
        @MavenDebug
        void packageSimplePhaseGenerateTestSources(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(HasDirCondition.emptyDirInTarget(Paths.get("classes"))) //
                    .has(HasDirCondition.nonEmptyDirInTarget(Paths.get("test-classes"))) //
                    .has(ContainsBuildersCondition.expectedBuilders(Simple.class.getPackage(), true));
            assertThat(result).out().warn().containsExactly("JAR will be empty - no content was marked for inclusion!");
        }

        @MavenTest
        @MavenDebug
        void simpleClassOnly(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilder(SimpleClass.class.getName() + "Builder", false));
            assertThat(result) //
                    .out() //
                    .info() //
                    .contains("Add class " + SimpleClass.class.getName() + '.');
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        void packageSimpleCustomExcludes(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilder(SimpleClass.class.getName() + "Builder", false)) //
                    .has(HasNoBuilderCondition.noBuilder(SimpleClassNoDefaultConstructor.class.getName() + "Builder")) //
                    .has(HasNoBuilderCondition.noBuilder(SimpleClassNoSetPrefix.class.getName() + "Builder")) //
                    .has(HasNoBuilderCondition.noBuilder(Child.class.getName() + "Builder")) //
                    .has(HasNoBuilderCondition.noBuilder(Parent.class.getName() + "Builder"));
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        void packageComplexCustomHierarchyCollectionExcludes(final MavenExecutionResult result) {
            final var expectedBuildersRootDir = Paths.get("src", "it", "resources", "expected-builders", "custom-hierarchy-collection-excludes");
            final var builderClass = ClassWithHierarchy.class.getName() + "Builder";
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilder(builderClass, false, expectedBuildersRootDir));
            assertThat(result).out().warn().isEmpty();
        }
    }

    @Nested
    @MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
    class WithCustomInvalidConfig {

        @MavenTest
        void noIncludes(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .anySatisfy(s -> Assertions.assertThat(s) //
                            .containsSubsequence( //
                                    "Failed to execute goal io.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                    "generate-builders (default) on project", //
                                    "The parameters 'includes' for goal", //
                                    "are missing or invalid -> [Help 1]"));
        }

        @MavenTest
        void includeNoFieldSpecified(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .contains( //
                            "Invalid <include> tag. Exactly one of the fields packageName or className needs to be initialized.", //
                            "-> [Help 1]");
        }

        @MavenTest
        void includeAllFieldsSpecified(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .contains( //
                            "Invalid <include> tag. Exactly one of the fields packageName or className needs to be initialized.", //
                            "-> [Help 1]");
        }

        @MavenTest
        void excludeNoFieldSpecified(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .contains( //
                            "Invalid <exclude> tag. Exactly one of the fields packageName, packageRegex, className or classRegex needs to be initialized.", //
                            "-> [Help 1]");
        }

        @MavenTest
        void excludeTwoFieldsSpecified(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .contains( //
                            "Invalid <exclude> tag. Exactly one of the fields packageName, packageRegex, className or classRegex needs to be initialized.", //
                            "-> [Help 1]");
        }

        @MavenTest
        void hierarchyCollectionExcludeNoFieldSpecified(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .contains( //
                            "Invalid <exclude> tag. Exactly one of the fields packageName, packageRegex, className or classRegex needs to be initialized.", //
                            "-> [Help 1]");
        }

        @MavenTest
        void hierarchyCollectionExcludeTwoFieldsSpecified(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .contains( //
                            "Invalid <exclude> tag. Exactly one of the fields packageName, packageRegex, className or classRegex needs to be initialized.", //
                            "-> [Help 1]");
        }
    }

    @Nested
    @MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
    class ExecutionFailure {

        @MavenTest
        void invalidTargetDirectory(final MavenExecutionResult result) {
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
                                    "Failed to execute goal io.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                    "generate-builders (default) on project", //
                                    "Could not create target directory", //
                                    pomXml, //
                                    "-> [Help 1]"));
        }

        @MavenTest
        void builderFileCannotBeWritten(final MavenExecutionResult result) {
            assertThat(result).isFailure();
            final var srcMainJava = result.getMavenProjectResult().getTargetProjectDirectory() //
                    .resolve("src").resolve("main").resolve("java") //
                    .toAbsolutePath() //
                    .toString();
            assertThat(result) //
                    .out() //
                    .info() //
                    .contains( //
                            "Scan package io.github.tobi.laa.reflective.fluent.builders.test.models.simple recursively for classes.", //
                            "Found 5 classes for which to generate builders.", //
                            "Make sure target directory " + srcMainJava + " exists.");
            assertThat(result) //
                    .out() //
                    .error() //
                    .anySatisfy(s -> Assertions.assertThat(s) //
                            .containsSubsequence( //
                                    "Failed to execute goal io.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                    "generate-builders (default) on project", //
                                    "Could not create file for builder for " + SimpleClass.class.getName(), //
                                    "SimpleClassBuilder.java: Is a directory -> [Help 1]"));
        }

        @MavenTest
        void classNotFound(final MavenExecutionResult result) {
            assertThat(result) //
                    .isFailure() //
                    .out() //
                    .error() //
                    .anySatisfy(s -> Assertions.assertThat(s) //
                            .containsSubsequence( //
                                    "Failed to execute goal io.github.tobi-laa:reflective-fluent-builders-maven-plugin", //
                                    "generate-builders (default) on project", //
                                    "Unable to load class does.not.exist -> [Help 1]"));
        }
    }

    @Nested
    @MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
    class IncrementalBuild {

        @MavenTest
        @SystemProperty(value = "incrementalBuildForIntegrationTests", content = "true")
        void packageSimpleNotAllBuilderFilesExist(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Simple.class.getPackage(), false));
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
                            "Generate builder for class " + Parent.class.getName())
                    .doesNotContain("All builders are up-to-date, skipping generation.");
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        @SystemProperty(value = "incrementalBuildForIntegrationTests", content = "true")
        void packageSimpleAllBuilderFilesExistAndBuildContextHasDelta(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Simple.class.getPackage(), false));
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
                            "Generate builder for class " + Parent.class.getName())
                    .doesNotContain("All builders are up-to-date, skipping generation.");
            assertThat(result).out().warn().isEmpty();
        }

        @MavenTest
        @SystemProperty(value = "incrementalBuildForIntegrationTests", content = "true")
        @SystemProperty(value = "fixedNoDeltaForIntegrationTests", content = "true")
        void packageSimpleAllBuilderFilesExistAndBuildContextHasNoDelta(final MavenExecutionResult result) {
            assertThat(result) //
                    .isSuccessful() //
                    .project() //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilders(Simple.class.getPackage(), false));
            final var targetDirectory = projectResultHelper.getGeneratedSourcesDir(result.getMavenProjectResult()).resolve("builders");
            assertThat(result) //
                    .out() //
                    .info() //
                    .doesNotContain( //
                            "Make sure target directory " + targetDirectory + " exists.", //
                            "Generate builder for class " + Child.class.getName(), //
                            "Generate builder for class " + SimpleClass.class.getName(), //
                            "Generate builder for class " + Parent.class.getName())
                    .contains( //
                            "Scan package " + Simple.class.getPackage().getName() + " recursively for classes.", //
                            "Found 5 classes for which to generate builders.", //
                            "All builders are up-to-date, skipping generation.");
            assertThat(result).out().warn().containsExactly("JAR will be empty - no content was marked for inclusion!");
        }
    }

    @Nested
    @MavenRepository(MAVEN_SHARED_LOCAL_CACHE)
    class MultiModuleBuild {

        @MavenTest
        void multiModuleBuild(final MavenExecutionResult result) {
            final var expectedBuildersRootDir = Paths.get("src", "it", "resources", "expected-builders", "multi-module-project");
            assertThat(result).isSuccessful();
            assertThat(result) //
                    .project() //
                    .withModule("module1") //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilder( //
                            "io.github.tobi.laa.reflective.fluent.builders.test.models.DogBuilder", //
                            true, //
                            expectedBuildersRootDir.resolve("module1")));
            assertThat(result) //
                    .project() //
                    .withModule("module2") //
                    .hasTarget() //
                    .has(ContainsBuildersCondition.expectedBuilder( //
                            "io.github.tobi.laa.reflective.fluent.builders.test.models.CatBuilder", //
                            true, //
                            expectedBuildersRootDir.resolve("module2")));
            assertThat(result).out().warn().isEmpty();
        }
    }
}
