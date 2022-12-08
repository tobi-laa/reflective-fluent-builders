package com.github.tobi.laa.reflective.fluent.builders.archunit;

import com.github.tobi.laa.reflective.fluent.builders.ArchUnitMarker;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

/**
 * <p>
 * Main class for executing ArchUnit tests.
 * </p>
 */
@AnalyzeClasses(packagesOf = ArchUnitMarker.class, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchUnitTests {

    @ArchTest
    private final ArchTests codingRules = ArchTests.in(CodingRules.class);

    @ArchTest
    private final ArchTests dependencyRules = ArchTests.in(DependencyRules.class);

    @ArchTest
    private final ArchTests interfaceRules = ArchTests.in(InterfaceRules.class);

    @ArchTest
    private final ArchTests namingConventionRules = ArchTests.in(NamingConventionRules.class);
}