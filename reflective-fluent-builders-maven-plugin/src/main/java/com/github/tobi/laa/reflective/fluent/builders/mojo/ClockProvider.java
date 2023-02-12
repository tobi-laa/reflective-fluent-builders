package com.github.tobi.laa.reflective.fluent.builders.mojo;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.time.Clock;

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
        return Clock.systemDefaultZone();
    }
}
