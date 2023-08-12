package io.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.codehaus.plexus.logging.AbstractLogEnabled;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Objects;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * <p>
 * Provides a {@link ClassLoader} that includes the maven project's dependencies as well.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class ClassLoaderProvider extends AbstractLogEnabled implements Provider<ClassLoader> {

    @lombok.NonNull
    private final MavenBuild mavenBuild;

    @Override
    public ClassLoader get() {
        return new URLClassLoader(getClasspathElementUrls(), getSystemClassLoader());
    }

    private URL[] getClasspathElementUrls() {
        final List<String> classpathElements = getClasspathElements();
        final URL[] classpathElementUrls = new URL[classpathElements.size()];
        for (int i = 0; i < classpathElements.size(); i++) {
            logAddingToClassLoader(classpathElements.get(i));
            classpathElementUrls[i] = toUrl(new File(classpathElements.get(i)));
        }
        return classpathElementUrls;
    }

    private List<String> getClasspathElements() {
        try {
            return mavenBuild.getClasspathElements();
        } catch (final DependencyResolutionRequiredException e) {
            throw new ClassLoaderConstructionException("Error while resolving dependencies of maven project.", e);
        }
    }

    private void logAddingToClassLoader(final Object resource) {
        getLogger().debug("Attempt to add " + resource + " to ClassLoader.");
    }

    private URL toUrl(final File file) {
        Objects.requireNonNull(file);
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException e) {
            throw new ClassLoaderConstructionException("Error while attempting to convert file " + file + " to URL.", e);
        }
    }

    static class ClassLoaderConstructionException extends RuntimeException {

        private static final long serialVersionUID = -8178484321714698102L;

        private ClassLoaderConstructionException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
