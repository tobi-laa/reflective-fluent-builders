package io.github.tobi.laa.reflective.fluent.builders.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;

/**
 * <p>
 * Compares {@link Type types} in the context of a setter method - that is types with generics are considered equal even
 * if their type arguments do not match.
 * </p>
 */
class SetterTypeComparator implements Comparator<Type> {
    
    @Override
    public int compare(final Type a, final Type b) {
        if (a == null) {
            return -1;
        } else if (b == null) {
            return 1;
        } else if (a instanceof ParameterizedType && b instanceof ParameterizedType) {
            final var aRawType = ((ParameterizedType) a).getRawType();
            final var bRawType = ((ParameterizedType) b).getRawType();
            return aRawType.getTypeName().compareTo(bRawType.getTypeName());
        } else {
            return a.getTypeName().compareTo(b.getTypeName());
        }
    }
}
