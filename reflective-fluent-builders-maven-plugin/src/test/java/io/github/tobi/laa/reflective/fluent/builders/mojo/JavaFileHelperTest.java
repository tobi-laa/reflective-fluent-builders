package io.github.tobi.laa.reflective.fluent.builders.mojo;

import com.google.common.io.Files;
import io.github.tobi.laa.reflective.fluent.builders.test.models.nested.TopLevelClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import lombok.SneakyThrows;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class JavaFileHelperTest {

    private final JavaFileHelper javaFileHelper = new JavaFileHelper();

    private Class<?> givenClass;
    private Package givenPackage;
    private String givenJavaName;
    private Path givenPath;

    private ThrowingCallable method;

    private Path actualPath;
    private String actualJavaName;

    @BeforeEach
    void resetTestData() {
        givenClass = null;
        givenPackage = null;
        givenJavaName = null;
        givenPath = null;
        method = null;
        actualPath = null;
        actualJavaName = null;
    }

    @Test
    void givenClassNull_whenCallingClassToPath_throwsNullPointerException() {
        givenClass(null);
        whenCallingClassToPath();
        thenNullPointerExceptionIsThrown();
    }

    @Test
    void givenPackageNull_whenCallingPackageToPath_throwsNullPointerException() {
        givenPackage(null);
        whenCallingPackageToPath();
        thenNullPointerExceptionIsThrown();
    }

    @Test
    void givenJavaNameNull_whenCallingJavaNameToPath_throwsNullPointerException() {
        givenJavaName(null);
        whenCallingJavaNameToPath();
        thenNullPointerExceptionIsThrown();
    }

    @Test
    void givenPathNull_whenCallingPathToJavaName_throwsNullPointerException() {
        givenPath(null);
        whenCallingPathToJavaName();
        thenNullPointerExceptionIsThrown();
    }

    @ParameterizedTest
    @ArgumentsSource(ClassProvider.class)
    void givenValidClass_whenCallingClassToPath_returnsExpectedPath(final Class<?> givenClass, final Path expectedPath) {
        givenClass(givenClass);
        whenCallingClassToPath();
        thenPathIs(expectedPath);
    }

    @ParameterizedTest
    @ArgumentsSource(PackageProvider.class)
    void givenValidPackage_whenCallingPackageToPath_returnsExpectedPath(final Package givenPackage, final Path expectedPath) {
        givenPackage(givenPackage);
        whenCallingPackageToPath();
        thenPathIs(expectedPath);
    }

    @ParameterizedTest
    @ArgumentsSource(JavaNameProvider.class)
    void givenValidJavaName_whenCallingJavaNameToPath_returnsExpectedPath(final String givenJavaName, final Path expectedPath) {
        givenJavaName(givenJavaName);
        whenCallingJavaNameToPath();
        thenPathIs(expectedPath);
    }

    @ParameterizedTest
    @ArgumentsSource(PathProvider.class)
    void givenValidPath_whenCallingPathToJavaName_returnsExpectedJavaName(final Path givenPath, final String expectedJavaName) {
        givenPath(givenPath);
        whenCallingPathToJavaName();
        thenJavaNameIs(expectedJavaName);
    }

    private void givenClass(final Class<?> givenClass) {
        this.givenClass = givenClass;
    }

    private void givenPackage(final Package givenPackage) {
        this.givenPackage = givenPackage;
    }

    private void givenJavaName(final String givenJavaName) {
        this.givenJavaName = givenJavaName;
    }

    private void givenPath(final Path givenPath) {
        this.givenPath = givenPath;
    }

    private void whenCallingClassToPath() {
        method = () -> actualPath = javaFileHelper.classToPath(givenClass);
    }

    private void whenCallingPackageToPath() {
        method = () -> actualPath = javaFileHelper.packageToPath(givenPackage);
    }

    private void whenCallingJavaNameToPath() {
        method = () -> actualPath = javaFileHelper.javaNameToPath(givenJavaName);
    }

    private void whenCallingPathToJavaName() {
        method = () -> actualJavaName = javaFileHelper.pathToJavaName(givenPath);
    }

    private void thenNullPointerExceptionIsThrown() {
        assertThatThrownBy(method).isInstanceOf(NullPointerException.class);
    }

    private void thenPathIs(final Path expectedPath) {
        assertThatCode(method).doesNotThrowAnyException();
        assertThat(actualPath).isEqualTo(expectedPath);
    }

    private void thenJavaNameIs(final String expectedJavaName) {
        assertThatCode(method).doesNotThrowAnyException();
        assertThat(actualJavaName).isEqualTo(expectedJavaName);
    }

    private static class ClassProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(String.class, Paths.get("java").resolve("lang").resolve("String.class")),
                    Arguments.of(
                            SimpleClass.class,
                            Paths.get("io", "github", "tobi", "laa", "reflective", "fluent", "builders", "test", "models", "simple", "SimpleClass.class")),
                    Arguments.of(
                            TopLevelClass.NestedPublicLevelOne.class,
                            Paths.get("io", "github", "tobi", "laa", "reflective", "fluent", "builders", "test", "models", "nested", "TopLevelClass$NestedPublicLevelOne.class")),
                    Arguments.of(
                            TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.class,
                            Paths.get("io", "github", "tobi", "laa", "reflective", "fluent", "builders", "test", "models", "nested", "TopLevelClass$NestedPublicLevelOne$NestedPublicLevelTwo.class")));
        }
    }

    private static class PackageProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext extensionContext) {
            final Path nested = Paths.get("io", "github", "tobi", "laa", "reflective", "fluent", "builders", "test", "models", "nested");
            return Stream.of(
                    Arguments.of(String.class.getPackage(), Paths.get("java").resolve("lang")),
                    Arguments.of(
                            SimpleClass.class.getPackage(),
                            Paths.get("io", "github", "tobi", "laa", "reflective", "fluent", "builders", "test", "models", "simple")),
                    Arguments.of(
                            TopLevelClass.NestedPublicLevelOne.class.getPackage(),
                            nested),
                    Arguments.of(
                            TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.class.getPackage(),
                            nested));
        }
    }

    private static class JavaNameProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.concat(
                            new ClassProvider().provideArguments(extensionContext),
                            new PackageProvider().provideArguments(extensionContext))
                    .map(args -> Arguments.of(getName(args.get()[0]), dropFileNameExtension(args.get()[1])));
        }

        private Path dropFileNameExtension(final Object obj) {
            final Path path = (Path) obj;
            return path.resolveSibling(Files.getNameWithoutExtension(path.getFileName().toString()));
        }
    }

    private static class PathProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.concat(
                            new ClassProvider().provideArguments(extensionContext),
                            new PackageProvider().provideArguments(extensionContext))
                    .map(args -> Arguments.of(args.get()[1], getName(args.get()[0])));
        }
    }

    @SneakyThrows
    private static String getName(final Object classOrPackage) {
        final Method getName = classOrPackage.getClass().getDeclaredMethod("getName");
        getName.setAccessible(true);
        return (String) getName.invoke(classOrPackage);
    }
}