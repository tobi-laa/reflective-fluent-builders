package io.github.tobi.laa.reflective.fluent.builders.generator.model;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Holds information about an inner class encapsulating fields. This is mainly done to avoid name collisions.
 * </p>
 */
@Data
@Builder
public class EncapsulatingClassSpec {

    @lombok.NonNull
    private final FieldSpec field;

    @lombok.NonNull
    private final TypeSpec innerClass;
}
