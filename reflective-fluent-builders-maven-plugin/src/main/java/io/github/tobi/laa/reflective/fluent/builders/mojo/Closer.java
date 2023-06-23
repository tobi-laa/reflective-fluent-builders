package io.github.tobi.laa.reflective.fluent.builders.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.logging.AbstractLogEnabled;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Closeable;
import java.io.IOException;

/**
 * <p>
 * Can be used for {@link Closeable#close() closing} an object if it implements {@link Closeable}. Encapsulated for
 * exception handling and testing purposes.
 * </p>
 */
@Singleton
@Named
class Closer extends AbstractLogEnabled {

    /**
     * <p>
     * Attempts to close {@code object} if it implements {@link Closeable}. Otherwise does nothing.
     * </p>
     *
     * @param object The object to be closed if it implements {@link Closeable}. Must not be {@code null}.
     * @throws MojoExecutionException In case an {@link IOException error} occurs while attempting to close {@code classLoader}.
     */
    <T> void closeIfCloseable(final T object) throws MojoExecutionException {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (final IOException e) {
                throw new MojoExecutionException("Error while attempting to close " + object.getClass() + '.', e);
            }
        } else {
            getLogger().debug("Not closing " + object + " of type " + object.getClass() + " as it does not implements Closeable.");
        }
    }
}