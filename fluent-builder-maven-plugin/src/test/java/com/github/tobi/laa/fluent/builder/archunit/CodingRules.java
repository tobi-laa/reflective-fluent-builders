package com.github.tobi.laa.fluent.builder.archunit;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

/**
 * <p>
 * Some general coding rules, in broad parts copied over from the ArchUnit examples.
 * </p>
 */
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
                    // FIXME rather go for matching of type than name
                    .haveFullName("log")
                    .or().haveFullName("LOG")
                    .or().haveFullName("logger")
                    .or().haveFullName("LOGGER")
                    .should().bePrivate()
                    .andShould().beStatic()
                    .andShould().beFinal()
                    .as("Loggers should private static final.")
                    .because("That is a convention for this project.")
                    .allowEmptyShould(true); // FIXME

    @ArchTest
    private final ArchRule noJodatime = NO_CLASSES_SHOULD_USE_JODATIME;

    @ArchTest
    private final ArchRule noFieldInjection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

}