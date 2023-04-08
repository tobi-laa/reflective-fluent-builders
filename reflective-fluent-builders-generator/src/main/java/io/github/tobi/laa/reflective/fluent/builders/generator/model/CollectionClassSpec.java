package io.github.tobi.laa.reflective.fluent.builders.generator.model;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * Holds information about an inner class which provides convenience methods for fluently adding elements to a
 * collection, a map or an array.
 * </p>
 */
@Data
@Builder
public class CollectionClassSpec {

    @lombok.NonNull
    private final MethodSpec getter;

    @lombok.NonNull
    private final TypeSpec innerClass;
}
