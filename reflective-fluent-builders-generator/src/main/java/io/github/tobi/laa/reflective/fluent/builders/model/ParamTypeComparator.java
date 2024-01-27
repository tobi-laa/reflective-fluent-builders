package io.github.tobi.laa.reflective.fluent.builders.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;

/**
 * <p>
 * Compares {@link PropertyType types} in the context of being a method parameter - that is types with generics are
 * considered equal even if their type arguments do not match.
 * </p>
 */
class ParamTypeComparator implements Comparator<PropertyType> {

    @Override
    public int compare(final PropertyType a, final PropertyType b) {
        if (a == null) {
            return -1;
        } else if (b == null) {
            return 1;
        } else if (a.getType() instanceof ParameterizedType && b.getType() instanceof ParameterizedType) {
            final Type aRawType = ((ParameterizedType) a.getType()).getRawType();
            final Type bRawType = ((ParameterizedType) b.getType()).getRawType();
            return aRawType.getTypeName().compareTo(bRawType.getTypeName());
        } else {
            return a.getType().getTypeName().compareTo(b.getType().getTypeName());
        }
    }
}
