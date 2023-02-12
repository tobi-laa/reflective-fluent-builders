package com.github.tobi.laa.reflective.fluent.builders;

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
class FixedClockProvider implements Provider<Clock> {

    @Override
    public Clock get() {
        return Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC"));
    }
}
