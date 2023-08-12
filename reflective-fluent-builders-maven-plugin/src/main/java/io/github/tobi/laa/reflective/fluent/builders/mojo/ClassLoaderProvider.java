package io.github.tobi.laa.reflective.fluent.builders.mojo;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.codehaus.plexus.logging.AbstractLogEnabled;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.io.Closeable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
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

    @lombok.NonNull
    private final Closer closer;

    URLClassLoader classLoader;

    private List<String> classLoaderElements;

    @Override
    public ClassLoader get() {
        if (classLoaderNeedsRecreation()) {
            recreateClassLoaderForMavenBuild();
        }
        return classLoader;
    }

    private boolean classLoaderNeedsRecreation() {
        if (classLoader == null) {
            return true;
        } else {
            final var elementsFromMavenBuild = new HashSet<>(getClasspathElements());
            final var elementsFromOldClassLoader = new HashSet<>(classLoaderElements);
            final var classLoaderDiffersFromMavenBuild = !elementsFromMavenBuild.equals(elementsFromOldClassLoader);
            if (classLoaderDiffersFromMavenBuild) {
                getLogger().debug("ClassLoader will be re-created as elements of underlying maven build have changed.");
            }
            return classLoaderDiffersFromMavenBuild;
        }
    }

    private void recreateClassLoaderForMavenBuild() {
        closeAndDisposeOfClassLoader();
        createClassLoaderForMavenBuild();
    }

    private void createClassLoaderForMavenBuild() {
        classLoaderElements = getClasspathElements();
        classLoader = new URLClassLoader(getClasspathElementUrls(), getSystemClassLoader());
    }

    /**
     * <p>
     * {@link Closeable#close() Closes} the {@link ClassLoader} that has been provided by this {@link ClassLoaderProvider}, if it
     * exists. Also removes any internal reference to said {@link ClassLoader}.
     * </p>
     * <p>
     * Calling this method guarantees that the next time {@link #get()} is called, a <em>new</em> instance of a
     * {@link ClassLoader} is returned.
     * </p>
     *
     * @throws ClassLoaderProviderException In case an error occurs while attempting to {@link Closeable#close() close} the
     *                                      underlying {@link ClassLoader}.
     */
    void closeAndDisposeOfClassLoader() {
        if (classLoader != null) {
            try {
                closer.closeIfCloseable(classLoader);
            } catch (final Closer.CloseException e) {
                throw new ClassLoaderProviderException("Error while closing old ClassLoader instance.", e);
            } finally {
                classLoader = null;
            }
        }
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
            throw new ClassLoaderProviderException("Error while resolving dependencies of maven project.", e);
        }
    }

    private void logAddingToClassLoader(final String resource) {
        getLogger().debug("Attempt to add " + resource + " to ClassLoader.");
    }

    private URL toUrl(final File file) {
        Objects.requireNonNull(file);
        try {
            return file.toURI().toURL();
        } catch (final MalformedURLException e) {
            throw new ClassLoaderProviderException("Error while attempting to convert file " + file + " to URL.", e);
        }
    }

    static class ClassLoaderProviderException extends RuntimeException {

        private static final long serialVersionUID = -8178484321714698102L;

        private ClassLoaderProviderException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
