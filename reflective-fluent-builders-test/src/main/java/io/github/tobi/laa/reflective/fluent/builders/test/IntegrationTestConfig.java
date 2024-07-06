package io.github.tobi.laa.reflective.fluent.builders.test;

import io.github.tobi.laa.reflective.fluent.builders.Marker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.inject.Provider;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * <p>
 * Imported by {@link IntegrationTest @IntegrationTest}.
 * </p>
 */
@Configuration
@ComponentScan(basePackageClasses = Marker.class)
@RequiredArgsConstructor
class IntegrationTestConfig {

    /**
     * <p>
     * Provides a {@link Clock} to be injected via DI.
     * </p>
     *
     * @return the {@link Clock} to be injected via DI.
     */
    @Bean
    Provider<Clock> fixedClockProvider() {
        return this::fixedClock;
    }

    /**
     * <p>
     * Provides a {@link Clock} to be injected via DI.
     * </p>
     *
     * @return the {@link Clock} to be injected via DI.
     */
    @Bean
    Clock fixedClock() {
        return Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC"));
    }

    /**
     * <p>
     * Provides a {@link ClassLoader} to be injected via DI.
     * </p>
     *
     * @return the {@link ClassLoader} to be injected via DI.
     */
    @Bean
    Provider<ClassLoader> classLoaderProvider() {
        return this::classLoader;
    }

    /**
     * <p>
     * Provides a {@link ClassLoader} to be injected via DI.
     * </p>
     *
     * @return the {@link ClassLoader} to be injected via DI.
     */
    @Bean
    ClassLoader classLoader() {
        return ClassLoader.getSystemClassLoader();
    }
}
