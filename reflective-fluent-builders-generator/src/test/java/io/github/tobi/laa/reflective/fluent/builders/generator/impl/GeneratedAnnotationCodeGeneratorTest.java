package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.AnnotationSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneratedAnnotationCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    private final GeneratedAnnotationCodeGenerator generator = new GeneratedAnnotationCodeGenerator(Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC")));

    @Test
    void testGenerateNull() {
        // Arrange
        final BuilderMetadata builderMetadata = null;
        // Act
        final Executable generate = () -> generator.generate(builderMetadata);
        // Assert
        assertThrows(NullPointerException.class, generate);
    }

    @Test
    void testGenerate() {
        // Arrange
        final BuilderMetadata builderMetadata = BuilderMetadata.builder() //
                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                .name("SimpleClassBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(SimpleClass.class)) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        // Act
        final AnnotationSpec actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).hasToString("@javax.annotation.processing.Generated(value = \"io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator\", date = \"3333-03-13T00:00Z[UTC]\")");
    }
}