package io.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.Location;

/**
 * <p>
 * Exclude {@link ArchUnitTests} itself.
 * </p>
 */
class DoNotIncludeSelf implements ImportOption {

    @Override
    public boolean includes(final Location location) {
        return !location.contains(ArchUnitTests.class.getSimpleName());
    }
}
