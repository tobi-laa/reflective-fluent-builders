package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.props.impl.StandardBuildersProperties;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * <p>
 * Provides a {@link BuildersProperties} to be injected via DI.
 * </p>
 */
@Named
@Singleton
@SuppressWarnings("unused")
class BuildersPropertiesProvider implements Provider<BuildersProperties> {

    @Override
    public BuildersProperties get() {
        return new StandardBuildersProperties();
    }
}