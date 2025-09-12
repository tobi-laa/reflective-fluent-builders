package io.github.tobi.laa.reflective.fluent.builders.test;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * {@link javax.inject.Inject Injects} the member as a {@link org.mockito.Mock mock}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@MockitoBean
public @interface InjectMock {
    // no content
}
