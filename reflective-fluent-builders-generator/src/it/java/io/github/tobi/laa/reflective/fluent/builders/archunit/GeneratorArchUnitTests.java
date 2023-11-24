package io.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import io.github.tobi.laa.reflective.fluent.builders.Marker;

/**
 * <p>
 * Main class for executing ArchUnit tests.
 * </p>
 */
@AnalyzeClasses(packagesOf = Marker.class)
class GeneratorArchUnitTests {

    @ArchTest
    private final ArchTests testDependencyRules = ArchTests.in(TestDependencyRules.class);
}