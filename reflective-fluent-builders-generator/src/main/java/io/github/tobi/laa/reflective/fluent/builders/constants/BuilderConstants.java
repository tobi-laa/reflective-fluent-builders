package io.github.tobi.laa.reflective.fluent.builders.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Holds constants used by multiples services, configuration or mojos.
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuilderConstants {

    /**
     * <p>
     * Reserved value which will be replaced with the current package at runtime and can thus be used if it is desired
     * to e.g. place generated builders within the same package as their corresponding classes or within a package
     * relative to them.
     * </p>
     */
    public static final String PACKAGE_PLACEHOLDER = "<PACKAGE_NAME>";

    /**
     * <p>
     * Name of the field which will hold the factory method to instantiate the object to be built by a builder.
     * </p>
     */
    public static final String OBJECT_SUPPLIER_FIELD_NAME = "objectSupplier";

    /**
     * <p>
     * Name of the marker field which is solely used to be able to detect generated builders via reflection at a later stage.
     * </p>
     */
    public static final String GENERATED_BUILDER_MARKER_FIELD_NAME = "______generatedByReflectiveFluentBuildersGenerator";

    /**
     * <p>
     * Class and field name for the inner class which is added to every generated builder for encapsulating the actual
     * field values to avoid name collisions.
     * </p>
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class FieldValue {

        public static final String CLASS_NAME = FieldValue.class.getSimpleName();

        public static final String FIELD_NAME = StringUtils.uncapitalize(FieldValue.class.getSimpleName());
    }

    /**
     * <p>
     * Class and field name for the inner class which is added to every generated builder for encapsulating flags
     * holding whether a setter should be called when the {@code build()}-method is executed. Encapsulation within an
     * inner class is done to avoid name collisions.
     * </p>
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CallSetterFor {

        public static final String CLASS_NAME = CallSetterFor.class.getSimpleName();

        public static final String FIELD_NAME = StringUtils.uncapitalize(CallSetterFor.class.getSimpleName());
    }
}
