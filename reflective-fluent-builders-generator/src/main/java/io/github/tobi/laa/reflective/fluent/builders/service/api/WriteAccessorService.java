package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.*;

import java.util.SortedSet;

/**
 * <p>
 * Gathers all write accessors found on a class.
 * </p>
 */
public interface WriteAccessorService {

    /**
     * <p>
     * Gathers all write accessors found on {@code clazz}.
     * </p>
     *
     * @param clazz The class for which to gather all write accessors. Must not be {@code null}.
     * @return All write accessors of {@code clazz}.
     */
    SortedSet<WriteAccessor> gatherAllWriteAccessors(final ClassInfo clazz);

    /**
     * <p>
     * Checks whether {@code writeAccessor} is a {@link Setter}.
     * </p>
     *
     * @param writeAccessor The {@link WriteAccessor} to check. Must not be {@code null}.
     * @return {@code true} if {@code writeAccessor} is a {@link Setter}, {@code false} otherwise.
     */
    boolean isSetter(final WriteAccessor writeAccessor);

    /**
     * <p>
     * Checks whether {@code writeAccessor} is the {@link Getter} of a {@link CollectionType collection}.
     * </p>
     *
     * @param writeAccessor The {@link WriteAccessor} to check. Must not be {@code null}.
     * @return {@code true} if {@code writeAccessor} is a collection getter, {@code false} otherwise.
     */
    boolean isCollectionGetter(final WriteAccessor writeAccessor);

    /**
     * <p>
     * Checks whether {@code writeAccessor} is an {@link Adder}.
     * </p>
     *
     * @param writeAccessor The {@link WriteAccessor} to check. Must not be {@code null}.
     * @return {@code true} if {@code writeAccessor} is an adder, {@code false} otherwise.
     */
    boolean isAdder(final WriteAccessor writeAccessor);

    /**
     * <p>
     * Drop the configured setter prefix (for instance {@code set}) from {@code name}.
     * </p>
     *
     * @param name The (method) name from which to drop the configured setter prefix. Must not be {@code null}.
     * @return {@code name} with the configured setter prefix stripped from it. If {@code name} does not start with said
     * prefix or solely consists of it and would thus be empty after stripping it, {@code name} will be returned
     * unchanged.
     */
    String dropSetterPrefix(final String name);

    /**
     * <p>
     * Drop the configured adder pattern (for instance {@code add(.+)}) surrounding {@code name}.
     * </p>
     *
     * @param name The (method) name from which to drop the configured adder pattern. Must not be {@code null}.
     * @return {@code name} with the configured setter prefix stripped from it. If {@code name} does not match the said
     * pattern, {@code name} will be returned unchanged.
     */
    String dropAdderPattern(final String name);

    /**
     * <p>
     * Drop the configured getter prefix (for instance {@code get}) from {@code name}.
     * </p>
     *
     * @param name The (method) name from which to drop the configured getter prefix. Must not be {@code null}.
     * @return {@code name} with the configured getter prefix stripped from it. If {@code name} does not start with said
     * prefix or solely consists of it and would thus be empty after stripping it, {@code name} will be returned
     * unchanged.
     */
    String dropGetterPrefix(final String name);

    /**
     * <p>
     * Checks whether this {@link WriteAccessor} is equivalent to {@code other}. Two {@link WriteAccessor} are
     * considered equivalent if they modify the same property of the same class.
     * </p>
     * <p>
     * Consider the following example:
     * </p>
     * <pre>
     * public class Person {
     *
     *     public List<String> items;
     *
     *     public List<String> getItems() {
     *         return this.items;
     *     }
     *
     *     public void setItems(final List<String> items) {
     *         this.items = items;
     *     }
     *
     *     public void addItem(final String item) {
     *         this.items.add(item);
     *     }
     * }
     * </pre>
     * <p>
     * In this case, when scanning the class {@code Person}, four {@link WriteAccessor WriteAccessors} would be found:
     * </p>
     * <ul>
     *     <li>A {@link WriteAccessor} for directly accessing the field {@code items}.</li>
     *     <li>A {@link WriteAccessor} for the setter {@code setItems}.</li>
     *     <li>A {@link WriteAccessor} for the getter {@code getItems}.</li>
     *     <li>A {@link WriteAccessor} for the adder {@code addItem}.</li>
     * </ul>
     * <p>
     * As all of those {@link WriteAccessor WriteAccessors} modify the same property of the same class, they are all
     * considered to be equivalent.
     * </p>
     *
     * @param first  The first {@link WriteAccessor}.
     * @param second The second {@link WriteAccessor}.
     * @return {@code true} if this {@code first} is equivalent to {@code second}, {@code false} otherwise.
     */
    boolean equivalentAccessors(final WriteAccessor first, final WriteAccessor second);
}
