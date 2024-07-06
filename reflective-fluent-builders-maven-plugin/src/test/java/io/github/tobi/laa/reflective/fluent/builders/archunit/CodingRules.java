package io.github.tobi.laa.reflective.fluent.builders.archunit;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

/**
 * <p>
 * Some general coding rules, in broad parts copied over from the ArchUnit examples.
 * </p>
 */
@SuppressWarnings("unused")
class CodingRules {

    @ArchTest
    private final ArchRule noAccessToStandardStreams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    private final ArchRule noGenericExceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    private final ArchRule noJavaUtilLogging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    @ArchTest
    private final ArchRule loggersShouldBePrivateStaticFinal =
            fields().that()
                    .haveRawType("org.codehaus.plexus.logging.Logger")
                    .or().haveRawType("org.apache.maven.plugin.logging.Log")
                    .or().haveRawType("org.slf4j.Logger")
                    .and().areNotAnnotatedWith("org.mockito.Mock")
                    .and().areNotAnnotatedWith("io.github.tobi.laa.reflective.fluent.builders.test.InjectMock")
                    .should().bePrivate()
                    .andShould().beStatic()
                    .andShould().beFinal()
                    .as("Loggers should private static final.")
                    .because("That is a convention for this project.")
                    .allowEmptyShould(true);

    @ArchTest
    private final ArchRule noJodatime = NO_CLASSES_SHOULD_USE_JODATIME;

    @ArchTest
    private final ArchRule noFieldInjection =
            noFields().that().areDeclaredInClassesThat().areNotAnnotatedWith(IntegrationTest.class)
                    .should(BE_ANNOTATED_WITH_AN_INJECTION_ANNOTATION)
                    .as("no classes should use field injection")
                    .because("field injection is considered harmful; use constructor injection or setter injection instead; "
                            + "see https://stackoverflow.com/q/39890849 for detailed explanations");

    @ArchTest
    private final ArchRule noGuavaReflection = noClasses() //
            .should() //
            .dependOnClassesThat() //
            .haveFullyQualifiedName("com.google.common.reflect.ClassPath") //
            .as("Guava's ClassPath should not be used.") //
            .because("The Guava documentation says to use ClassGraph instead.");

    @ArchTest
    private final ArchRule noSpringOutsideDedicatedPackage =
            noClasses()
                    .that().resideOutsideOfPackage("io.github.tobi.laa.reflective.fluent.builders.test")
                    .should().dependOnClassesThat().resideInAPackage("org.springframework..")
                    .as("Spring should not be used directly in tests.")
                    .because("The project relies on JSR-330 for dependency injection, not Spring. " +
                            "Usage of Spring should thus be hidden by custom test support classes.");
}