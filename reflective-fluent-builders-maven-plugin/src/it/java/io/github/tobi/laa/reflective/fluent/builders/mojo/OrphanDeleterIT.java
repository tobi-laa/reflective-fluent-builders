package io.github.tobi.laa.reflective.fluent.builders.mojo;

import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.InjectMock;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.Simple;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import lombok.SneakyThrows;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.codehaus.plexus.build.BuildContext;
import org.codehaus.plexus.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.MockedStatic;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.*;

@IntegrationTest
@SuppressWarnings("unused")
class OrphanDeleterIT {

    private static final Path PACKAGE_SIMPLE_DIR = Paths.get("io", "github", "tobi", "laa", "reflective", "fluent", "builders", "test", "models", "simple");

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private OrphanDeleter deleter;

    @InjectMock
    private BuildContext buildContext;

    @InjectMock
    private MavenProject mavenProject;

    @InjectMock
    private MojoExecution mojoExecution;

    @InjectMock
    private Logger logger;

    private MockedStatic<Files> filesMock;

    @TempDir
    private Path givenTarget;
    private Set<BuilderMetadata> givenMetadata;

    private ThrowingCallable deleteOrphans;

    @BeforeEach
    void mockFiles() {
        filesMock = mockStatic(Files.class, CALLS_REAL_METHODS);
    }

    @BeforeEach
    void resetTestData() {
        this.givenMetadata = null;
        this.deleteOrphans = null;
    }

    @BeforeEach
    void injectLogger() {
        deleter.enableLogging(logger);
    }

    @AfterEach
    void unmockFiles() {
        filesMock.close();
    }

    @ParameterizedTest
    @ArgumentsSource(NullArgsProvider.class)
    void givenNull_deletingOrphans_throwsNullPointerException(final Path target, final Set<BuilderMetadata> metadata) {
        givenTarget(target);
        givenMetadata(metadata);
        whenCallingDeleteOrphans();
        thenNullPointerExceptionIsThrown();
    }

    @Test
    void givenNonEmptyTarget_expectingNoBuilders_deletingOrphans_deletesEverything() {
        givenNonEmptyTarget();
        givenMetadata(emptySet());
        whenCallingDeleteOrphans();
        thenEverythingIsDeleted();
    }

    @Test
    void givenNonEmptyTarget_expectingSimpleClassBuilder_deletingOrphans_deletesChildBuilder() {
        givenNonEmptyTarget();
        givenMetadataForSimpleClassBuilder();
        whenCallingDeleteOrphans();
        thenChildBuilderIsDeleted();
    }

    @Test
    void givenEmptyTarget_expectingSimpleClassBuilder_deletingOrphans_doesNothing() {
        givenMetadataForSimpleClassBuilder();
        whenCallingDeleteOrphans();
        thenNothingHappens();
    }

    @Test
    void givenNonEmptyTarget_fileDeletionException_deletingOrphans_throwsMojoFailureException() {
        givenNonEmptyTarget();
        givenMetadata(emptySet());
        givenDeleteThrowsException();
        whenCallingDeleteOrphans();
        thenMojoFailureExceptionIsThrown();
    }

    private void givenTarget(final Path target) {
        this.givenTarget = target;
    }

    @SneakyThrows
    private void givenNonEmptyTarget() {
        Files.createDirectories(givenTarget.resolve(PACKAGE_SIMPLE_DIR).resolve("hierarchy"));
        Files.createFile(givenTarget.resolve(PACKAGE_SIMPLE_DIR).resolve("SimpleClassBuilder.java"));
        Files.createFile(givenTarget.resolve(PACKAGE_SIMPLE_DIR).resolve("hierarchy").resolve("ChildBuilder.java"));
    }

    private void givenMetadata(final Set<BuilderMetadata> metadata) {
        this.givenMetadata = metadata;
    }

    private void givenMetadataForSimpleClassBuilder() {
        givenMetadata(Collections.singleton(BuilderMetadata.builder()
                .packageName(Simple.class.getPackageName())
                .name("SimpleClassBuilder")
                .builtType(BuilderMetadata.BuiltType.builder()
                        .type(classInfo.get(SimpleClass.class))
                        .accessibleNonArgsConstructor(true)
                        .build())
                .build()));
    }

    private void givenDeleteThrowsException() {
        filesMock.when(() -> Files.delete(any(Path.class))).thenThrow(IOException.class);
    }

    private void whenCallingDeleteOrphans() {
        this.deleteOrphans = () -> deleter.deleteOrphanedBuilders(givenTarget, givenMetadata);
    }

    private void thenNullPointerExceptionIsThrown() {
        assertThatThrownBy(deleteOrphans).isInstanceOf(NullPointerException.class);
    }

    private void thenMojoFailureExceptionIsThrown() {
        assertThatThrownBy(deleteOrphans)
                .isInstanceOf(MojoFailureException.class)
                .hasMessage("Could not delete orphaned builders.")
                .hasCauseExactlyInstanceOf(IOException.class);
    }

    private void thenEverythingIsDeleted() {
        assertThatCode(deleteOrphans).doesNotThrowAnyException();
        assertThat(givenTarget).isEmptyDirectory();
        verify(logger, atLeastOnce()).info(matches("Deleting orphaned builder .+"));
        verify(logger, atLeastOnce()).info(matches("Deleting orphaned builder directory .+"));
    }

    private void thenChildBuilderIsDeleted() {
        assertThatCode(deleteOrphans).doesNotThrowAnyException();
        assertThat(givenTarget.resolve(PACKAGE_SIMPLE_DIR).resolve("SimpleClassBuilder.java")).isRegularFile();
        assertThat(givenTarget.resolve(PACKAGE_SIMPLE_DIR).resolve("hierarchy").resolve("ChildBuilder.java")).doesNotExist();
        verify(logger).info(matches("Deleting orphaned builder .+ChildBuilder.java"));
        verify(logger).info(matches("Deleting orphaned builder directory .+hierarchy"));
    }

    private void thenNothingHappens() {
        assertThatCode(deleteOrphans).doesNotThrowAnyException();
        assertThat(givenTarget).isEmptyDirectory();
        verifyNoInteractions(logger);
    }

    private static class NullArgsProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                    Arguments.of(null, null),
                    Arguments.of(null, Set.of()),
                    Arguments.of(Paths.get(""), null));
        }
    }
}
