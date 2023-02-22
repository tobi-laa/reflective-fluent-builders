package com.github.tobi.laa.reflective.fluent.builders.mojo;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class ClockProviderTest {

    @Test
    void testGetFixedClockForIntegrationTests() {
        // Arrange
        final ClockProvider clockProvider = new ClockProvider() {
            boolean useFixedClockForIntegrationTests() {
                return true;
            }
        };
        // Act
        final Clock actual = clockProvider.get();
        // Assert
        assertThat(actual).isEqualTo(Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC")));

    }

    @Test
    void testGet() {
        // Arrange
        final ClockProvider clockProvider = new ClockProvider() {
            boolean useFixedClockForIntegrationTests() {
                return false;
            }
        };
        // Act
        final Clock actual = clockProvider.get();
        // Assert
        assertThat(actual).isEqualTo(Clock.systemDefaultZone());

    }
}
