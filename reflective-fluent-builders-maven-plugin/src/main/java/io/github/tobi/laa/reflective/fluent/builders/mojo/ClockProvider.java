package io.github.tobi.laa.reflective.fluent.builders.mojo;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * <p>
 * Provides a {@link Clock} to be injected via DI.
 * </p>
 */
@Named
@Singleton
@SuppressWarnings("unused")
class ClockProvider implements Provider<Clock> {

    @Override
    public Clock get() {
        if (useFixedClockForIntegrationTests()) {
            return Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC"));
        } else {
            return Clock.systemDefaultZone();
        }
    }

    boolean useFixedClockForIntegrationTests() {
        return Boolean.parseBoolean(System.getenv("useFixedClockForIntegrationTests"));
    }
}
