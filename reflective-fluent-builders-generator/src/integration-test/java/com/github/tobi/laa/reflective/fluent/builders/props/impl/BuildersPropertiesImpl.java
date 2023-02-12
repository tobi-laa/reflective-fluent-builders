package com.github.tobi.laa.reflective.fluent.builders.props.impl;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Set;

/**
 * <p>
 * Implementation for integration tests.
 * </p>
 */
@Named
@Singleton
@Data
@NoArgsConstructor
class BuildersPropertiesImpl implements BuildersProperties {

    @lombok.NonNull
    private String builderPackage = BuilderConstants.PACKAGE_PLACEHOLDER;

    @lombok.NonNull
    private String builderSuffix = "Builder";

    @lombok.NonNull
    private String setterPrefix = "set";

    @lombok.NonNull
    private HierarchyCollection hierarchyCollection = new HierarchyCollectionImpl();

    @Data
    @NoArgsConstructor
    static class HierarchyCollectionImpl implements HierarchyCollection {

        @lombok.NonNull
        private Set<Class<?>> classesToExclude = Set.of(Object.class);
    }
}
