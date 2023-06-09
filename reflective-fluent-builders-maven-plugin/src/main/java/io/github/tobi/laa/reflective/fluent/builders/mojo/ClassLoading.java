package io.github.tobi.laa.reflective.fluent.builders.mojo;

import lombok.RequiredArgsConstructor;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.logging.AbstractLogEnabled;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * <p>
 * Encapsulates {@link ClassLoader}-related logic, specifically replacing the current thread's {@link ClassLoader} with
 * one that includes the maven project's dependencies as well. This class is thus <em>not</em> thread-safe.
 * </p>
 */
@Named
@RequiredArgsConstructor(onConstructor_ = @Inject)
class ClassLoading extends AbstractLogEnabled {

    @lombok.NonNull
    private final MavenBuild mavenBuild;

    @lombok.NonNull
    private final MojoParams params;

    ClassLoader oldClassLoader;

    URLClassLoader artifactIncludingClassLoader;

    void setThreadClassLoaderToArtifactIncludingClassLoader() throws MojoExecutionException {
        oldClassLoader = getThreadClassLoader();
        artifactIncludingClassLoader = constructArtifactIncludingClassLoader();
        setThreadClassLoader(artifactIncludingClassLoader);
    }

    void resetThreadClassLoader() throws MojoExecutionException {
        try {
            artifactIncludingClassLoader.close();
        } catch (final IOException e) {
            throw new MojoExecutionException("Error while attempting to close ClassLoader.", e);
        } finally {
            setThreadClassLoader(oldClassLoader);
            oldClassLoader = null;
            artifactIncludingClassLoader = null;
        }
    }

    Class<?> loadClass(final String className) throws MojoExecutionException {
        Objects.requireNonNull(className);
        try {
            return getThreadClassLoader().loadClass(className.trim());
        } catch (final ClassNotFoundException e) {
            throw new MojoExecutionException("Unable to load class " + className, e);
        }
    }

    private URLClassLoader constructArtifactIncludingClassLoader() throws MojoExecutionException {
        final var classUrls = Stream.concat( //
                        getOutputDirectoryUrls(), //
                        getUrlsOfArtifactsInScopesToInclude()) //
                .toArray(URL[]::new);
        return new URLClassLoader(classUrls, getThreadClassLoader());
    }

    private Stream<URL> getOutputDirectoryUrls() throws MojoExecutionException {
        final List<URL> outputDirectoryUrls = new ArrayList<>();
        logAddingToClassLoader(mavenBuild.getOutputDirectory());
        outputDirectoryUrls.add(toUrl(new File(mavenBuild.getOutputDirectory())));
        if (mavenBuild.isTestPhase()) {
            logAddingToClassLoader(mavenBuild.getTestOutputDirectory());
            outputDirectoryUrls.add(toUrl(new File(mavenBuild.getTestOutputDirectory())));
        }
        return outputDirectoryUrls.stream();
    }

    private Stream<URL> getUrlsOfArtifactsInScopesToInclude() throws MojoExecutionException {
        final List<URL> urlsOfArtifacts = new ArrayList<>();
        for (final var artifact : mavenBuild.getArtifacts()) {
            if (params.getScopesToInclude().contains(artifact.getScope())) {
                logAddingToClassLoader(artifact.getFile());
                urlsOfArtifacts.add(toUrl(artifact.getFile()));
            }
        }
        return urlsOfArtifacts.stream();
    }

    private void logAddingToClassLoader(final Object resource) {
        getLogger().debug("Attempt to add " + resource + " to ClassLoader.");
    }

    private URL toUrl(final File file) throws MojoExecutionException {
        Objects.requireNonNull(file);
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException e) {
            throw new MojoExecutionException("Error while attempting to convert file " + file + " to URL.", e);
        }
    }

    private ClassLoader getThreadClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    private void setThreadClassLoader(final ClassLoader classLoader) {
        Thread.currentThread().setContextClassLoader(classLoader);
    }
}
