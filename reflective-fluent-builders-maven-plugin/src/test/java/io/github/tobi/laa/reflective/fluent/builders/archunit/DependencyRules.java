package io.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

/**
 * <p>
 * Rules for dependencies between packages, in broad parts copied over from the ArchUnit examples.
 * </p>
 */
class DependencyRules {

    @ArchTest
    static final ArchRule noAccessesToUpperPackage = NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;
}
