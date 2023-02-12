package com.github.tobi.laa.reflective.fluent.builders;

import com.google.inject.AbstractModule;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class FixedClockModule extends AbstractModule {

    @Override
    protected void configure() {
        binder().bind(Clock.class)
                .toInstance(Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC")));
    }
}
