package com.github.tobi.laa.reflective.fluent.builders.generator.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.model.Visibility;
import com.github.tobi.laa.reflective.fluent.builders.generator.service.api.VisibilityService;

import java.lang.reflect.Modifier;

/**
 * <p>
 * Standard implementation of {@link VisibilityService}.
 * </p>
 */
public class VisibilityServiceImpl implements VisibilityService {

    @Override
    public Visibility toVisibility(final int modifiers) {
        if (Modifier.isPrivate(modifiers)) {
            return Visibility.PRIVATE;
        } else if (Modifier.isProtected(modifiers)) {
            return Visibility.PROTECTED;
        } else if (Modifier.isPublic(modifiers)) {
            return Visibility.PUBLIC;
        } else {
            return Visibility.PACKAGE_PRIVATE;
        }
    }
}
