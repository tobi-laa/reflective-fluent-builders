package io.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.tobi.laa.reflective.fluent.builders.test.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * <p>
 * Rules for dependencies used in tests.
 * </p>
 */
class TestDependencyRules {

    @ArchTest
    private final ArchRule usageOfSpringShouldNotBeVisible =
            noClasses()
                    .that().resideOutsideOfPackage(Test.class.getPackageName())
                    .should().dependOnClassesThat().resideInAPackage("org.springframework..")
                    .as("Spring should not be used directly in tests.")
                    .because("The project relies on JSR-330 for dependency injection, not Spring. Usage of Spring should thus be hidden by custom test support classes.");
}
