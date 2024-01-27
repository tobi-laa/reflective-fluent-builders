package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link TopLevelClass}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class TopLevelClassBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<TopLevelClass> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link TopLevelClass} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected TopLevelClassBuilder(final Supplier<TopLevelClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link TopLevelClassBuilder} that will work on a new instance of {@link TopLevelClass} once {@link #build()} is called.
   */
  public static TopLevelClassBuilder newInstance() {
    return new TopLevelClassBuilder(TopLevelClass::new);
  }

  /**
   * Creates an instance of {@link TopLevelClassBuilder} that will work on an instance of {@link TopLevelClass} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static TopLevelClassBuilder withSupplier(final Supplier<TopLevelClass> supplier) {
    return new TopLevelClassBuilder(supplier);
  }

  /**
   * Sets the value for the {@code nestedNonStatic} property.
   * To be more precise, this will lead to {@link TopLevelClass#setNestedNonStatic(TopLevelClass.NestedNonStatic)} being called on construction of the object.
   * @param nestedNonStatic the value to set.
   * @return This builder for chained calls.
   */
  public TopLevelClassBuilder nestedNonStatic(final TopLevelClass.NestedNonStatic nestedNonStatic) {
    this.fieldValue.nestedNonStatic = nestedNonStatic;
    this.callSetterFor.nestedNonStatic = true;
    return this;
  }

  /**
   * Sets the value for the {@code nestedPackagePrivate} property.
   * To be more precise, this will lead to {@link TopLevelClass#setNestedPackagePrivate(TopLevelClass.NestedPackagePrivateLevelOne)} being called on construction of the object.
   * @param nestedPackagePrivate the value to set.
   * @return This builder for chained calls.
   */
  public TopLevelClassBuilder nestedPackagePrivate(
      final TopLevelClass.NestedPackagePrivateLevelOne nestedPackagePrivate) {
    this.fieldValue.nestedPackagePrivate = nestedPackagePrivate;
    this.callSetterFor.nestedPackagePrivate = true;
    return this;
  }

  /**
   * Sets the value for the {@code nestedProtected} property.
   * To be more precise, this will lead to {@link TopLevelClass#setNestedProtected(TopLevelClass.NestedProtectedLevelOne)} being called on construction of the object.
   * @param nestedProtected the value to set.
   * @return This builder for chained calls.
   */
  public TopLevelClassBuilder nestedProtected(
      final TopLevelClass.NestedProtectedLevelOne nestedProtected) {
    this.fieldValue.nestedProtected = nestedProtected;
    this.callSetterFor.nestedProtected = true;
    return this;
  }

  /**
   * Sets the value for the {@code nestedPublic} property.
   * To be more precise, this will lead to {@link TopLevelClass#setNestedPublic(TopLevelClass.NestedPublicLevelOne)} being called on construction of the object.
   * @param nestedPublic the value to set.
   * @return This builder for chained calls.
   */
  public TopLevelClassBuilder nestedPublic(final TopLevelClass.NestedPublicLevelOne nestedPublic) {
    this.fieldValue.nestedPublic = nestedPublic;
    this.callSetterFor.nestedPublic = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link TopLevelClass}.
   * @return The constructed instance. Never {@code null}.
   */
  public TopLevelClass build() {
    final TopLevelClass objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.nestedNonStatic) {
      objectToBuild.setNestedNonStatic(this.fieldValue.nestedNonStatic);
    }
    if (this.callSetterFor.nestedPackagePrivate) {
      objectToBuild.setNestedPackagePrivate(this.fieldValue.nestedPackagePrivate);
    }
    if (this.callSetterFor.nestedProtected) {
      objectToBuild.setNestedProtected(this.fieldValue.nestedProtected);
    }
    if (this.callSetterFor.nestedPublic) {
      objectToBuild.setNestedPublic(this.fieldValue.nestedPublic);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean nestedNonStatic;

    boolean nestedPackagePrivate;

    boolean nestedProtected;

    boolean nestedPublic;
  }

  private class FieldValue {
    TopLevelClass.NestedNonStatic nestedNonStatic;

    TopLevelClass.NestedPackagePrivateLevelOne nestedPackagePrivate;

    TopLevelClass.NestedProtectedLevelOne nestedProtected;

    TopLevelClass.NestedPublicLevelOne nestedPublic;
  }

  /**
   * Builder for {@link TopLevelClass.NestedPackagePrivateLevelOne}.
   */
  @Generated(
      value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
      date = "3333-03-13T00:00Z[UTC]"
  )
  public static class NestedPackagePrivateLevelOneBuilder {
    /**
     * This field is solely used to be able to detect generated builders via reflection at a later stage.
     */
    @SuppressWarnings("unused")
    private boolean ______generatedByReflectiveFluentBuildersGenerator;

    private final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> objectSupplier;

    private final CallSetterFor callSetterFor = new CallSetterFor();

    private final FieldValue fieldValue = new FieldValue();

    /**
     * Creates a new instance of {@link TopLevelClass.NestedPackagePrivateLevelOne} using the given {@code objectSupplier}.
     * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
     */
    protected NestedPackagePrivateLevelOneBuilder(
        final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    /**
     * Creates an instance of {@link NestedPackagePrivateLevelOneBuilder} that will work on a new instance of {@link TopLevelClass.NestedPackagePrivateLevelOne} once {@link #build()} is called.
     */
    public static NestedPackagePrivateLevelOneBuilder newInstance() {
      return new NestedPackagePrivateLevelOneBuilder(TopLevelClass.NestedPackagePrivateLevelOne::new);
    }

    /**
     * Creates an instance of {@link NestedPackagePrivateLevelOneBuilder} that will work on an instance of {@link TopLevelClass.NestedPackagePrivateLevelOne} that is created initially by the given {@code supplier} once {@link #build()} is called.
     */
    public static NestedPackagePrivateLevelOneBuilder withSupplier(
        final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> supplier) {
      return new NestedPackagePrivateLevelOneBuilder(supplier);
    }

    /**
     * Sets the value for the {@code field} property.
     * To be more precise, this will lead to {@link TopLevelClass.NestedPackagePrivateLevelOne#setField(int)} being called on construction of the object.
     * @param field the value to set.
     * @return This builder for chained calls.
     */
    public NestedPackagePrivateLevelOneBuilder field(final int field) {
      this.fieldValue.field = field;
      this.callSetterFor.field = true;
      return this;
    }

    /**
     * Performs the actual construction of an instance for {@link TopLevelClass.NestedPackagePrivateLevelOne}.
     * @return The constructed instance. Never {@code null}.
     */
    public TopLevelClass.NestedPackagePrivateLevelOne build() {
      final TopLevelClass.NestedPackagePrivateLevelOne objectToBuild = this.objectSupplier.get();
      if (this.callSetterFor.field) {
        objectToBuild.setField(this.fieldValue.field);
      }
      return objectToBuild;
    }

    private class CallSetterFor {
      boolean field;
    }

    private class FieldValue {
      int field;
    }
  }

  /**
   * Builder for {@link TopLevelClass.NestedProtectedLevelOne}.
   */
  @Generated(
      value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
      date = "3333-03-13T00:00Z[UTC]"
  )
  public static class NestedProtectedLevelOneBuilder {
    /**
     * This field is solely used to be able to detect generated builders via reflection at a later stage.
     */
    @SuppressWarnings("unused")
    private boolean ______generatedByReflectiveFluentBuildersGenerator;

    private final Supplier<TopLevelClass.NestedProtectedLevelOne> objectSupplier;

    private final CallSetterFor callSetterFor = new CallSetterFor();

    private final FieldValue fieldValue = new FieldValue();

    /**
     * Creates a new instance of {@link TopLevelClass.NestedProtectedLevelOne} using the given {@code objectSupplier}.
     * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
     */
    protected NestedProtectedLevelOneBuilder(
        final Supplier<TopLevelClass.NestedProtectedLevelOne> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    /**
     * Creates an instance of {@link NestedProtectedLevelOneBuilder} that will work on a new instance of {@link TopLevelClass.NestedProtectedLevelOne} once {@link #build()} is called.
     */
    public static NestedProtectedLevelOneBuilder newInstance() {
      return new NestedProtectedLevelOneBuilder(TopLevelClass.NestedProtectedLevelOne::new);
    }

    /**
     * Creates an instance of {@link NestedProtectedLevelOneBuilder} that will work on an instance of {@link TopLevelClass.NestedProtectedLevelOne} that is created initially by the given {@code supplier} once {@link #build()} is called.
     */
    public static NestedProtectedLevelOneBuilder withSupplier(
        final Supplier<TopLevelClass.NestedProtectedLevelOne> supplier) {
      return new NestedProtectedLevelOneBuilder(supplier);
    }

    /**
     * Sets the value for the {@code field} property.
     * To be more precise, this will lead to {@link TopLevelClass.NestedProtectedLevelOne#setField(int)} being called on construction of the object.
     * @param field the value to set.
     * @return This builder for chained calls.
     */
    public NestedProtectedLevelOneBuilder field(final int field) {
      this.fieldValue.field = field;
      this.callSetterFor.field = true;
      return this;
    }

    /**
     * Performs the actual construction of an instance for {@link TopLevelClass.NestedProtectedLevelOne}.
     * @return The constructed instance. Never {@code null}.
     */
    public TopLevelClass.NestedProtectedLevelOne build() {
      final TopLevelClass.NestedProtectedLevelOne objectToBuild = this.objectSupplier.get();
      if (this.callSetterFor.field) {
        objectToBuild.setField(this.fieldValue.field);
      }
      return objectToBuild;
    }

    private class CallSetterFor {
      boolean field;
    }

    private class FieldValue {
      int field;
    }
  }

  /**
   * Builder for {@link TopLevelClass.NestedPublicLevelOne}.
   */
  @Generated(
      value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
      date = "3333-03-13T00:00Z[UTC]"
  )
  public static class NestedPublicLevelOneBuilder {
    /**
     * This field is solely used to be able to detect generated builders via reflection at a later stage.
     */
    @SuppressWarnings("unused")
    private boolean ______generatedByReflectiveFluentBuildersGenerator;

    private final Supplier<TopLevelClass.NestedPublicLevelOne> objectSupplier;

    private final CallSetterFor callSetterFor = new CallSetterFor();

    private final FieldValue fieldValue = new FieldValue();

    /**
     * Creates a new instance of {@link TopLevelClass.NestedPublicLevelOne} using the given {@code objectSupplier}.
     * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
     */
    protected NestedPublicLevelOneBuilder(
        final Supplier<TopLevelClass.NestedPublicLevelOne> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    /**
     * Creates an instance of {@link NestedPublicLevelOneBuilder} that will work on a new instance of {@link TopLevelClass.NestedPublicLevelOne} once {@link #build()} is called.
     */
    public static NestedPublicLevelOneBuilder newInstance() {
      return new NestedPublicLevelOneBuilder(TopLevelClass.NestedPublicLevelOne::new);
    }

    /**
     * Creates an instance of {@link NestedPublicLevelOneBuilder} that will work on an instance of {@link TopLevelClass.NestedPublicLevelOne} that is created initially by the given {@code supplier} once {@link #build()} is called.
     */
    public static NestedPublicLevelOneBuilder withSupplier(
        final Supplier<TopLevelClass.NestedPublicLevelOne> supplier) {
      return new NestedPublicLevelOneBuilder(supplier);
    }

    /**
     * Sets the value for the {@code nested} property.
     * To be more precise, this will lead to {@link TopLevelClass.NestedPublicLevelOne#setNested(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo)} being called on construction of the object.
     * @param nested the value to set.
     * @return This builder for chained calls.
     */
    public NestedPublicLevelOneBuilder nested(
        final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested) {
      this.fieldValue.nested = nested;
      this.callSetterFor.nested = true;
      return this;
    }

    /**
     * Performs the actual construction of an instance for {@link TopLevelClass.NestedPublicLevelOne}.
     * @return The constructed instance. Never {@code null}.
     */
    public TopLevelClass.NestedPublicLevelOne build() {
      final TopLevelClass.NestedPublicLevelOne objectToBuild = this.objectSupplier.get();
      if (this.callSetterFor.nested) {
        objectToBuild.setNested(this.fieldValue.nested);
      }
      return objectToBuild;
    }

    private class CallSetterFor {
      boolean nested;
    }

    private class FieldValue {
      TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested;
    }

    /**
     * Builder for {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo}.
     */
    @Generated(
        value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
        date = "3333-03-13T00:00Z[UTC]"
    )
    public static class NestedPublicLevelTwoBuilder {
      /**
       * This field is solely used to be able to detect generated builders via reflection at a later stage.
       */
      @SuppressWarnings("unused")
      private boolean ______generatedByReflectiveFluentBuildersGenerator;

      private final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> objectSupplier;

      private final CallSetterFor callSetterFor = new CallSetterFor();

      private final FieldValue fieldValue = new FieldValue();

      /**
       * Creates a new instance of {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo} using the given {@code objectSupplier}.
       * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
       */
      protected NestedPublicLevelTwoBuilder(
          final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> objectSupplier) {
        this.objectSupplier = Objects.requireNonNull(objectSupplier);
      }

      /**
       * Creates an instance of {@link NestedPublicLevelTwoBuilder} that will work on a new instance of {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo} once {@link #build()} is called.
       */
      public static NestedPublicLevelTwoBuilder newInstance() {
        return new NestedPublicLevelTwoBuilder(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo::new);
      }

      /**
       * Creates an instance of {@link NestedPublicLevelTwoBuilder} that will work on an instance of {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo} that is created initially by the given {@code supplier} once {@link #build()} is called.
       */
      public static NestedPublicLevelTwoBuilder withSupplier(
          final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> supplier) {
        return new NestedPublicLevelTwoBuilder(supplier);
      }

      /**
       * Sets the value for the {@code nested} property.
       * To be more precise, this will lead to {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo#setNested(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree)} being called on construction of the object.
       * @param nested the value to set.
       * @return This builder for chained calls.
       */
      public NestedPublicLevelTwoBuilder nested(
          final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested) {
        this.fieldValue.nested = nested;
        this.callSetterFor.nested = true;
        return this;
      }

      /**
       * Performs the actual construction of an instance for {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo}.
       * @return The constructed instance. Never {@code null}.
       */
      public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo build() {
        final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo objectToBuild = this.objectSupplier.get();
        if (this.callSetterFor.nested) {
          objectToBuild.setNested(this.fieldValue.nested);
        }
        return objectToBuild;
      }

      private class CallSetterFor {
        boolean nested;
      }

      private class FieldValue {
        TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested;
      }

      /**
       * Builder for {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree}.
       */
      @Generated(
          value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
          date = "3333-03-13T00:00Z[UTC]"
      )
      public static class NestedPublicLevelThreeBuilder {
        /**
         * This field is solely used to be able to detect generated builders via reflection at a later stage.
         */
        @SuppressWarnings("unused")
        private boolean ______generatedByReflectiveFluentBuildersGenerator;

        private final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> objectSupplier;

        private final CallSetterFor callSetterFor = new CallSetterFor();

        private final FieldValue fieldValue = new FieldValue();

        /**
         * Creates a new instance of {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree} using the given {@code objectSupplier}.
         * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
         */
        protected NestedPublicLevelThreeBuilder(
            final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> objectSupplier) {
          this.objectSupplier = Objects.requireNonNull(objectSupplier);
        }

        /**
         * Creates an instance of {@link NestedPublicLevelThreeBuilder} that will work on a new instance of {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree} once {@link #build()} is called.
         */
        public static NestedPublicLevelThreeBuilder newInstance() {
          return new NestedPublicLevelThreeBuilder(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree::new);
        }

        /**
         * Creates an instance of {@link NestedPublicLevelThreeBuilder} that will work on an instance of {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree} that is created initially by the given {@code supplier} once {@link #build()} is called.
         */
        public static NestedPublicLevelThreeBuilder withSupplier(
            final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> supplier) {
          return new NestedPublicLevelThreeBuilder(supplier);
        }

        /**
         * Sets the value for the {@code field} property.
         * To be more precise, this will lead to {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree#setField(int)} being called on construction of the object.
         * @param field the value to set.
         * @return This builder for chained calls.
         */
        public NestedPublicLevelThreeBuilder field(final int field) {
          this.fieldValue.field = field;
          this.callSetterFor.field = true;
          return this;
        }

        /**
         * Performs the actual construction of an instance for {@link TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree}.
         * @return The constructed instance. Never {@code null}.
         */
        public TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree build(
            ) {
          final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree objectToBuild = this.objectSupplier.get();
          if (this.callSetterFor.field) {
            objectToBuild.setField(this.fieldValue.field);
          }
          return objectToBuild;
        }

        private class CallSetterFor {
          boolean field;
        }

        private class FieldValue {
          int field;
        }
      }
    }
  }
}
