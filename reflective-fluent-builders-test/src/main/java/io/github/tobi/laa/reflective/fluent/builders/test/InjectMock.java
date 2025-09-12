package io.github.tobi.laa.reflective.fluent.builders.test;

import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * {@link javax.inject.Inject Injects} the member as a {@link org.mockito.Mock mock}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@MockBean
public @interface InjectMock {
    // no content
}
