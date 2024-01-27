package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.classgraph.ClassInfo;
import lombok.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableSortedSet.toImmutableSortedSet;
import static java.util.Comparator.naturalOrder;
import static java.util.Objects.compare;

/**
 * <p>
 * Holds metadata about a builder class (as well the class the builder builds).
 * </p>
 */
@lombok.Builder
@Data
public class BuilderMetadata implements Comparable<BuilderMetadata> {

    @lombok.NonNull
    private final String packageName;

    @lombok.NonNull
    private final String name;

    @lombok.NonNull
    private final BuiltType builtType;

    /**
     * <p>
     * The types of exceptions that can be thrown by the builder's {@code build} method. If no exceptions can be thrown,
     * this set is empty. The exceptions contained are the condensed exceptions of all {@link WriteAccessor}s.
     * </p>
     */
    @lombok.NonNull
    @Singular
    private final Set<Class<? extends Throwable>> exceptionTypes;

    /**
     * <p>
     * Builders that will be nested within this builder. Nested builders correspond to nested static classes within the
     * built type.
     * </p>
     */
    @lombok.NonNull
    @Singular
    private final SortedSet<BuilderMetadata> nestedBuilders;

    @With
    @EqualsAndHashCode.Exclude
    private final BuilderMetadata enclosingBuilder;

    BuilderMetadata(
            @NonNull final String packageName,
            @NonNull final String name,
            @NonNull final BuiltType builtType,
            @NonNull final Set<Class<? extends Throwable>> exceptionTypes,
            @NonNull final SortedSet<BuilderMetadata> nestedBuilders,
            final BuilderMetadata enclosingBuilder) {
        this.packageName = packageName;
        this.name = name;
        this.builtType = builtType;
        this.exceptionTypes = exceptionTypes;
        this.nestedBuilders = nestedBuilders.stream()
                .map(nestedBuilder -> nestedBuilder.withEnclosingBuilder(this))
                .collect(toImmutableSortedSet(naturalOrder()));
        this.enclosingBuilder = enclosingBuilder;
    }

    /**
     * <p>
     * The builder that encloses this builder. If this builder is a top-level builder, this field is {@code null}.
     * </p>
     *
     * @return The builder that encloses this builder or {@link Optional#empty()} if this builder is a top-level builder.
     */
    public Optional<BuilderMetadata> getEnclosingBuilder() {
        return Optional.ofNullable(enclosingBuilder);
    }

    @Override
    public int compareTo(@NonNull final BuilderMetadata other) {
        if (equals(other)) {
            return 0;
        } else {
            return Stream.<IntSupplier>of( //
                            () -> compare(packageName, other.packageName, naturalOrder()), //
                            () -> compare(name, other.name, naturalOrder())) //
                    .map(IntSupplier::getAsInt) //
                    .filter(i -> i != 0) //
                    .findFirst() //
                    .orElse(1);
        }
    }

    @lombok.Builder
    @Data
    public static class BuiltType {

        @lombok.NonNull
        private final ClassInfo type;

        private final Path location;

        private final Path sourceFile;

        /**
         * <p>
         * Where the built type is located on the filesystem. Might point to a {@code class} or a {@code jar} file but
         * might also be absent.
         * </p>
         *
         * @return The location of the built type or {@link Optional#empty()} if the location is unknown.
         */
        public Optional<Path> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * <p>
         * The <em>name</em> of the source file of the built type such as {@code BuilderMetadata.class} or
         * {@code INeedToTry.kt}. Might be {@code null}.
         * </p>
         *
         * @return The name of the source file of the built type or {@link Optional#empty()} if the name is unknown.
         */
        public Optional<Path> getSourceFile() {
            return Optional.of(type).map(ClassInfo::getSourceFile).map(Paths::get);
        }

        private final boolean accessibleNonArgsConstructor;

        @lombok.NonNull
        @Singular
        private final SortedSet<WriteAccessor> writeAccessors;
    }
}
