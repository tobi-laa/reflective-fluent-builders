package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import java.lang.SuppressWarnings;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

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

  protected TopLevelClassBuilder(final Supplier<TopLevelClass> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static TopLevelClassBuilder newInstance() {
    return new TopLevelClassBuilder(TopLevelClass::new);
  }

  public static TopLevelClassBuilder withSupplier(final Supplier<TopLevelClass> supplier) {
    return new TopLevelClassBuilder(supplier);
  }

  public TopLevelClassBuilder nestedNonStatic(final TopLevelClass.NestedNonStatic nestedNonStatic) {
    this.fieldValue.nestedNonStatic = nestedNonStatic;
    this.callSetterFor.nestedNonStatic = true;
    return this;
  }

  public TopLevelClassBuilder nestedPackagePrivate(
      final TopLevelClass.NestedPackagePrivateLevelOne nestedPackagePrivate) {
    this.fieldValue.nestedPackagePrivate = nestedPackagePrivate;
    this.callSetterFor.nestedPackagePrivate = true;
    return this;
  }

  public TopLevelClassBuilder nestedProtected(
      final TopLevelClass.NestedProtectedLevelOne nestedProtected) {
    this.fieldValue.nestedProtected = nestedProtected;
    this.callSetterFor.nestedProtected = true;
    return this;
  }

  public TopLevelClassBuilder nestedPublic(final TopLevelClass.NestedPublicLevelOne nestedPublic) {
    this.fieldValue.nestedPublic = nestedPublic;
    this.callSetterFor.nestedPublic = true;
    return this;
  }

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

    protected NestedPackagePrivateLevelOneBuilder(
        final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    public static NestedPackagePrivateLevelOneBuilder newInstance() {
      return new NestedPackagePrivateLevelOneBuilder(TopLevelClass.NestedPackagePrivateLevelOne::new);
    }

    public static NestedPackagePrivateLevelOneBuilder withSupplier(
        final Supplier<TopLevelClass.NestedPackagePrivateLevelOne> supplier) {
      return new NestedPackagePrivateLevelOneBuilder(supplier);
    }

    public NestedPackagePrivateLevelOneBuilder field(final int field) {
      this.fieldValue.field = field;
      this.callSetterFor.field = true;
      return this;
    }

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

    protected NestedProtectedLevelOneBuilder(
        final Supplier<TopLevelClass.NestedProtectedLevelOne> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    public static NestedProtectedLevelOneBuilder newInstance() {
      return new NestedProtectedLevelOneBuilder(TopLevelClass.NestedProtectedLevelOne::new);
    }

    public static NestedProtectedLevelOneBuilder withSupplier(
        final Supplier<TopLevelClass.NestedProtectedLevelOne> supplier) {
      return new NestedProtectedLevelOneBuilder(supplier);
    }

    public NestedProtectedLevelOneBuilder field(final int field) {
      this.fieldValue.field = field;
      this.callSetterFor.field = true;
      return this;
    }

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

    protected NestedPublicLevelOneBuilder(
        final Supplier<TopLevelClass.NestedPublicLevelOne> objectSupplier) {
      this.objectSupplier = Objects.requireNonNull(objectSupplier);
    }

    public static NestedPublicLevelOneBuilder newInstance() {
      return new NestedPublicLevelOneBuilder(TopLevelClass.NestedPublicLevelOne::new);
    }

    public static NestedPublicLevelOneBuilder withSupplier(
        final Supplier<TopLevelClass.NestedPublicLevelOne> supplier) {
      return new NestedPublicLevelOneBuilder(supplier);
    }

    public NestedPublicLevelOneBuilder nested(
        final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo nested) {
      this.fieldValue.nested = nested;
      this.callSetterFor.nested = true;
      return this;
    }

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

      protected NestedPublicLevelTwoBuilder(
          final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> objectSupplier) {
        this.objectSupplier = Objects.requireNonNull(objectSupplier);
      }

      public static NestedPublicLevelTwoBuilder newInstance() {
        return new NestedPublicLevelTwoBuilder(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo::new);
      }

      public static NestedPublicLevelTwoBuilder withSupplier(
          final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo> supplier) {
        return new NestedPublicLevelTwoBuilder(supplier);
      }

      public NestedPublicLevelTwoBuilder nested(
          final TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree nested) {
        this.fieldValue.nested = nested;
        this.callSetterFor.nested = true;
        return this;
      }

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

        protected NestedPublicLevelThreeBuilder(
            final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> objectSupplier) {
          this.objectSupplier = Objects.requireNonNull(objectSupplier);
        }

        public static NestedPublicLevelThreeBuilder newInstance() {
          return new NestedPublicLevelThreeBuilder(TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree::new);
        }

        public static NestedPublicLevelThreeBuilder withSupplier(
            final Supplier<TopLevelClass.NestedPublicLevelOne.NestedPublicLevelTwo.NestedPublicLevelThree> supplier) {
          return new NestedPublicLevelThreeBuilder(supplier);
        }

        public NestedPublicLevelThreeBuilder field(final int field) {
          this.fieldValue.field = field;
          this.callSetterFor.field = true;
          return this;
        }

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
