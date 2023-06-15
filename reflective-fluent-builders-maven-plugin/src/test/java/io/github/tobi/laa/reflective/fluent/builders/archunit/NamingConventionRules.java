package io.github.tobi.laa.reflective.fluent.builders.archunit;

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
                    .and().areNotMemberClasses()
                    .should().haveSimpleNameEndingWith("ServiceImpl");

    @ArchTest
    static ArchRule serviceImplementationsShouldNotBeInterfaces =
            classes()
                    .that().resideInAPackage("..service.impl..")
                    .should().notBeInterfaces();

    @ArchTest
    static ArchRule generatorsShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..generator.api..")
                    .should().haveSimpleNameEndingWith("Generator");

    @ArchTest
    static ArchRule generatorsShouldBeInterfaces =
            classes()
                    .that().resideInAPackage("..generator.api..")
                    .should().beInterfaces();

    @ArchTest
    static ArchRule generatorImplementationsShouldNotBeInterfaces =
            classes()
                    .that().resideInAPackage("..generator.impl..")
                    .should().notBeInterfaces();
}