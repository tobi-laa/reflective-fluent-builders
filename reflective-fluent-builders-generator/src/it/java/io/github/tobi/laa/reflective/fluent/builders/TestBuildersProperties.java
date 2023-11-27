package io.github.tobi.laa.reflective.fluent.builders;

import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.props.impl.StandardBuildersProperties;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * <p>
 * Provides a {@link BuildersProperties} to be injected via DI.
 * </p>
 */
@SuppressWarnings("unused")
@Singleton
@Named
class TestBuildersProperties extends StandardBuildersProperties {
    // no content
}
