package io.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;

/**
 * <p>
 * Exclude the module {@code reflective-fluent-builders-test-models} which may contain all sorts of weird constructs.
 * </p>
 */
class ExcludeTestModels implements ImportOption {

    @Override
    public boolean includes(final Location location) {
        return !location.contains("reflective-fluent-builders-test-models");
    }
}
