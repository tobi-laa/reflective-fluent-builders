package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Paths;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.Collections.emptySortedSet;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BuilderMetadataTest {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @ParameterizedTest
    @MethodSource
    void testConstructionNull(final String packageName,
                              final String name,
                              final BuilderMetadata.BuiltType builtType,
                              final Set<Class<? extends Throwable>> exceptionTypes,
                              final SortedSet<BuilderMetadata> nestedBuilders,
                              final BuilderMetadata enclosingBuilder) {
        // Act
        final ThrowingCallable construct = () -> new BuilderMetadata(
                packageName,
                name,
                builtType,
                exceptionTypes,
                nestedBuilders,
                enclosingBuilder);
        // Assert
        assertThatThrownBy(construct).isExactlyInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> testConstructionNull() {
        final var builtType = BuilderMetadata.BuiltType.builder() //
                .type(classInfo.get(SimpleClass.class.getName())) //
                .location(Paths.get("foo")) //
                .accessibleNonArgsConstructor(true) //
                .build();
        return Stream.of(
                Arguments.of(null, "ItemBuilder", builtType, emptySet(), emptySortedSet(), null),
                Arguments.of("io.example.pack", null, builtType, emptySet(), emptySortedSet(), null),
                Arguments.of("io.example.pack", "ItemBuilder", null, emptySet(), emptySortedSet(), null),
                Arguments.of("io.example.pack", "ItemBuilder", builtType, null, emptySortedSet(), null),
                Arguments.of("io.example.pack", "ItemBuilder", builtType, emptySet(), null, null));
    }

    @Test
    @SuppressWarnings("all")
    void testCompareToNull() {
        // Arrange
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName("io.example.pack") //
                .name("ItemBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(SimpleClass.class.getName())) //
                        .location(Paths.get("foo")) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        // Act
        final ThrowingCallable compareTo = () -> builderMetadata.compareTo(null);
        // Assert
        assertThatThrownBy(compareTo).isExactlyInstanceOf(NullPointerException.class);
    }
}
