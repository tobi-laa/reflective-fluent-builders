package com.github.tobi.laa.fluent.builder.archunit;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * <p>
 * Rules for naming conventions, in parts copied over from the ArchUnit examples.
 * </p>
 */
class NamingConventionRules {

    @ArchTest
    static ArchRule servicesShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..service.api..")
                    .should().haveSimpleNameEndingWith("Service");

    @ArchTest
    static ArchRule servicesShouldBeInterfaces =
            classes()
                    .that().resideInAPackage("..service.api..")
                    .should().beInterfaces();

    @ArchTest
    static ArchRule serviceImplementationsShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..service.impl..")
                    .should().haveSimpleNameEndingWith("ServiceImpl");

    @ArchTest
    static ArchRule getServiceImplementationsShouldNotBeInterfaces =
            classes()
                    .that().resideInAPackage("..service.impl..")
                    .should().notBeInterfaces();
}