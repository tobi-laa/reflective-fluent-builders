package com.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.model.Visibility;
import com.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;

import javax.inject.Singleton;
import java.lang.reflect.Modifier;

/**
 * <p>
 * Standard implementation of {@link VisibilityService}.
 * </p>
 */
@Singleton
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
