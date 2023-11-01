package io.github.tobi.laa.reflective.fluent.builders.sisu;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * Test classes annotated with this will be properly injected via Guice/Sisu.
 * </p>
 *
 * @see SisuExtension
 */
@Retention(RUNTIME)
@ExtendWith(SisuExtension.class)
public @interface SisuTest {
    // no content
}