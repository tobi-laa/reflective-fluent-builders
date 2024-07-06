package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.DirectFieldAccess;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.PUBLIC;
import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class SetterMethodNameGeneratorIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private SetterMethodNameGenerator generator;

    @Test
    void testGenerateNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final Executable generate = () -> generator.generate(writeAccessor);
        // Assert
        assertThrows(NullPointerException.class, generate);
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final WriteAccessor writeAccessor, final String expected) {
        // Act
        final String actual = generator.generate(writeAccessor);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> testGenerate() {
        return Stream.of( //
                Arguments.of(
                        Setter.builder()
                                .methodName("setAnInt")
                                .propertyName("somethingElse")
                                .propertyType(new SimpleType(int.class))
                                .visibility(PUBLIC)
                                .declaringClass(SimpleClass.class)
                                .build(),
                        "anInt"), //
                Arguments.of(Getter.builder()
                                .methodName("getIntCollection")
                                .propertyName("somethingElse")
                                .propertyType(new CollectionType(parameterize(List.class, String.class), String.class))
                                .visibility(PUBLIC)
                                .declaringClass(SimpleClass.class)
                                .build(),
                        "intCollection"), //
                Arguments.of(FieldAccessor.builder()
                                .propertyName("anInt")
                                .propertyType(new SimpleType(int.class))
                                .visibility(PUBLIC)
                                .declaringClass(DirectFieldAccess.class)
                                .build(),
                        "anInt"), //
                Arguments.of(Adder.builder() //
                                .methodName("addAnInt") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .propertyName("whoCaresAboutThis") //
                                .paramName("alsoWhoCaresAboutThat") //
                                .paramType(new SimpleType(String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(),
                        "anInt"));
    }
}
