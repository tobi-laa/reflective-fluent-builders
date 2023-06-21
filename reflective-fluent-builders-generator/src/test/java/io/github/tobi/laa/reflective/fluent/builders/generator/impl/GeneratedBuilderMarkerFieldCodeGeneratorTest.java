package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.FieldSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.GENERATED_BUILDER_MARKER_FIELD_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class GeneratedBuilderMarkerFieldCodeGeneratorTest {

    private final GeneratedBuilderMarkerFieldCodeGenerator generator = new GeneratedBuilderMarkerFieldCodeGenerator();

    @Test
    void testGenerate() {
        // Arrange
        final BuilderMetadata builderMetadata = BuilderMetadata.builder() //
                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                .name("SimpleClassBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(SimpleClass.class) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        // Act
        final FieldSpec actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.toString()).isEqualToIgnoringNewLines(String.format("private boolean %s;\n", GENERATED_BUILDER_MARKER_FIELD_NAME));
    }
}
