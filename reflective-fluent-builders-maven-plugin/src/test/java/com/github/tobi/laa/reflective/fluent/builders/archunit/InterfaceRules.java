package com.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * <p>
 * Rules for interfaces, in broad parts copied over from the ArchUnit examples.
 * </p>
 */
class InterfaceRules {

    @ArchTest
    private final ArchRule interfacesShouldNotHaveNamesEndingWithTheWordInterface =
            noClasses().that().areInterfaces().should().haveNameMatching(".*Interface");

    @ArchTest
    private final ArchRule interfacesShouldNotHaveSimpleClassNamesContainingTheWordInterface =
            noClasses().that().areInterfaces().should().haveSimpleNameContaining("Interface");

    @ArchTest
    private final ArchRule interfacesMustNotBePlacedInImplementationPackages =
            noClasses().that().resideInAPackage("..impl..").should().beInterfaces();

}