package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.FieldSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.GENERATED_BUILDER_MARKER_FIELD_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class GeneratedBuilderMarkerFieldCodeGeneratorTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    private final GeneratedBuilderMarkerFieldCodeGenerator generator = new GeneratedBuilderMarkerFieldCodeGenerator();

    @Test
    void testGenerate() {
        // Arrange
        final BuilderMetadata builderMetadata = BuilderMetadata.builder() //
                .packageName("com.github.tobi.laa.reflective.fluent.builders.test.models.simple") //
                .name("SimpleClassBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(SimpleClass.class)) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        // Act
        final FieldSpec actual = generator.generate(builderMetadata);
        // Assert
        assertThat(actual).isNotNull();
        //
        //
        //
        //
        assertThat(actual.toString())
                .isEqualToIgnoringNewLines(String.format( //
                        """
                                /**
                                 * This field is solely used to be able to detect generated builders via reflection at a later stage.
                                 */
                                @java.lang.SuppressWarnings("all")
                                private boolean %s;
                                """, //
                        GENERATED_BUILDER_MARKER_FIELD_NAME));
    }
}
