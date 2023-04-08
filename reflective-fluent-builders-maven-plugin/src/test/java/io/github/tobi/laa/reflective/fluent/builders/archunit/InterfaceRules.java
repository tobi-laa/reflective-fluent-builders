package io.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.AnInterface;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.AnotherInterface;
import io.github.tobi.laa.reflective.fluent.builders.test.models.unbuildable.Interface;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * <p>
 * Rules for interfaces, in broad parts copied over from the ArchUnit examples.
 * </p>
 */
class InterfaceRules {

    @ArchTest
    private final ArchRule interfacesShouldNotHaveNamesEndingWithTheWordInterface =
            noClasses()
                    .that().areInterfaces()
                    // exclusion for test models
                    .and().areNotAssignableTo(Interface.class)
                    .and().areNotAssignableTo(AnInterface.class)
                    .and().areNotAssignableTo(AnotherInterface.class)
                    .should().haveNameMatching(".*Interface");

    @ArchTest
    private final ArchRule interfacesShouldNotHaveSimpleClassNamesContainingTheWordInterface =
            noClasses()
                    .that().areInterfaces()
                    // exclusion for test models
                    .and().areNotAssignableTo(Interface.class)
                    .and().areNotAssignableTo(AnInterface.class)
                    .and().areNotAssignableTo(AnotherInterface.class)
                    .should().haveSimpleNameContaining("Interface");

    @ArchTest
    private final ArchRule interfacesMustNotBePlacedInImplementationPackages =
            noClasses().that().resideInAPackage("..impl..").should().beInterfaces();

}