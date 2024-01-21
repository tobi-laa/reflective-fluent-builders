package io.github.tobi.laa.reflective.fluent.builders.model;

import java.lang.reflect.Type;

/**
 * <p>
 * Represents a way of accessing the property of a class for which a builder is going to be generated. This can be
 * either a method such as a setter or the field itself.
 * </p>
 * <p>
 * To be more specific, all of the following might be considered {@link WriteAccessor};
 * </p>
 * <ul>
 *     <li>Setter methods such as
 *     <pre>
 *         {@code public void setAge(final int age)}
 *     </pre>
 *     </li>
 *     <li>For (presumably) collections or maps, adder methods such as
 *     <pre>
 *         {@code public void addItem(final String item)}
 *         {@code public void putMapping(final String key, final String value)}
 *     </pre>
 *     </li>
 *     <li>In the case of collections and maps, getters might also be considered write accessors given possible
 *     get-and-add or get-and-put constructs.
 *     <pre>
 *         {@code public List<String> getItems()}
 *         {@code public Map<String, String> getMap()}
 *     </pre>
 *     </li>
 *     <li>Fields such as
 *     <pre>
 *         {@code public int age;}
 *     </pre>
 *     </li>
 * </ul>
 */
public interface WriteAccessor extends Comparable<WriteAccessor> {

    /**
     * <p>
     * The type of this {@link WriteAccessor}. This might reference the parameter of a setter method, the return value
     * of a getter method or the type of field. For instance {@code int.class}.
     * </p>
     *
     * @return The type of this {@link WriteAccessor}.
     */
    Type getPropertyType();

    /**
     * <p>
     * The name of the property (presumed to be) modified by this {@link WriteAccessor}, for instance {@code age}.
     * </p>
     *
     * @return The name of the property modified by this {@link WriteAccessor}.
     */
    String getPropertyName();

    /**
     * <p>
     * The visibility of this {@link WriteAccessor}, for instance {@code PUBLIC}.
     * </p>
     *
     * @return The visibility of this {@link WriteAccessor}.
     */
    Visibility getVisibility();

    /**
     * <p>
     * The class within which this {@link WriteAccessor} is defined. This is particularly important for write accessors
     * inherited from super classes or interfaces.
     * </p>
     *
     * @return The class within which this {@link WriteAccessor} is defined.
     */
    Class<?> getDeclaringClass();

    /**
     * <p>
     * Creates a <em>new</em> {@link WriteAccessor} with all values kept the same except for {@code propertyName}.
     * </p>
     *
     * @param propertyName The new property name for the newly constructed {@link WriteAccessor}.
     * @return A new {@link WriteAccessor} with all values kept the same except for {@code paramName}.
     */
    WriteAccessor withPropertyName(final String propertyName);
}
