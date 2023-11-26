package io.github.tobi.laa.reflective.fluent.builders.test;

import io.github.tobi.laa.reflective.fluent.builders.Marker;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.props.impl.StandardBuildersProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * <p>
 * Imported by {@link IntegrationTest @IntegrationTest}.
 * </p>
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackageClasses = Marker.class)
class IntegrationTestConfig {

    /**
     * <p>
     * Provides a {@link BuildersProperties} to be injected via DI.
     * </p>
     */
    @Bean
    BuildersProperties props() {
        return new StandardBuildersProperties();
    }

    /**
     * <p>
     * Provides a {@link Clock} to be injected via DI.
     * </p>
     */
    @Bean
    Clock fixedClock() {
        return Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC"));
    }

    @Bean
    ClassLoader classLoader() {
        return ClassLoader.getSystemClassLoader();
    }
}
