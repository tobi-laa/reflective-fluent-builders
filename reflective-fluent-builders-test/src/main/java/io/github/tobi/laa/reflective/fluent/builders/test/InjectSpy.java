package io.github.tobi.laa.reflective.fluent.builders.test;

import org.springframework.boot.test.mock.mockito.SpyBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * {@link javax.inject.Inject Injects} the member, but wraps it within a {@link org.mockito.Spy spy}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@SpyBean
public @interface InjectSpy {
    // no content
}
