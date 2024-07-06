package io.github.tobi.laa.reflective.fluent.builders.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * Test classes annotated with this will be properly injected with all necessary dependencies.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@ContextConfiguration(classes = IntegrationTestConfig.class)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public @interface IntegrationTest {
    // no content
}
