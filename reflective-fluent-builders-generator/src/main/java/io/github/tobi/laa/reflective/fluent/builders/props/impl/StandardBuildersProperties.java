package io.github.tobi.laa.reflective.fluent.builders.props.impl;

import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.function.Predicate;

/**
 * <p>
 * Simple implementation of {@link BuildersProperties}. As it does <em>not</em> contain
 * {@link javax.inject.Named JSR-330 annotations} it will not be automatically be picked up by classpath scanning such
 * as it is performed by Sisu or Spring. If desired, an instance of this class will have to manually be added to the
 * DI context.
 * </p>
 */
@Data
@NoArgsConstructor
public class StandardBuildersProperties implements BuildersProperties {

    @lombok.NonNull
    private String builderPackage = BuilderConstants.PACKAGE_PLACEHOLDER;

    @lombok.NonNull
    private String builderSuffix = "Builder";

    @lombok.NonNull
    private String setterPrefix = "set";

    @lombok.NonNull
    private String getterPrefix = "get";

    @lombok.NonNull
    private String adderPattern = "add(.+)";

    private boolean getAndAddEnabled = true;

    private boolean directFieldAccessEnabled = true;

    private boolean addersEnabled = true;

    @lombok.NonNull
    @ToString.Exclude
    private Set<Predicate<Class<?>>> excludes = Set.of( //
            clazz -> clazz.getSimpleName().endsWith("Builder"), //
            clazz -> clazz.getSimpleName().endsWith("BuilderImpl"));

    @lombok.NonNull
    private StandardHierarchyCollection hierarchyCollection = new StandardHierarchyCollection();

    @Data
    @NoArgsConstructor
    public static class StandardHierarchyCollection implements HierarchyCollection {

        @lombok.NonNull
        @ToString.Exclude
        private Set<Predicate<Class<?>>> excludes = Set.of(Object.class::equals);
    }
}
