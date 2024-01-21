package io.github.tobi.laa.reflective.fluent.builders.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * Not a <em>setter</em> per se, but rather the getter of a {@link java.util.Collection collection}, so for instance a
 * list or a set, which can be chained with a call to {@link java.util.Collection#add(Object) add}.
 * </p>
 */
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class CollectionGetAndAdder extends CollectionSetter {

    @Override
    public CollectionGetAndAdder withPropertyName(final String propertyName) {
        return toBuilder().propertyName(propertyName).build();
    }
}
