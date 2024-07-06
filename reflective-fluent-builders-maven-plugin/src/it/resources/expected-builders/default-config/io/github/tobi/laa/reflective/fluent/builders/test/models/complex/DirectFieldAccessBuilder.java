package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

/**
 * Builder for {@link DirectFieldAccess}.
 */
@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class DirectFieldAccessBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("all")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<DirectFieldAccess> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  /**
   * Creates a new instance of {@link DirectFieldAccess} using the given {@code objectSupplier}.
   * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
   */
  protected DirectFieldAccessBuilder(final Supplier<DirectFieldAccess> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  /**
   * Creates an instance of {@link DirectFieldAccessBuilder} that will work on a new instance of {@link DirectFieldAccess} once {@link #build()} is called.
   */
  public static DirectFieldAccessBuilder newInstance() {
    return new DirectFieldAccessBuilder(DirectFieldAccess::new);
  }

  /**
   * Creates an instance of {@link DirectFieldAccessBuilder} that will work on an instance of {@link DirectFieldAccess} that is created initially by the given {@code supplier} once {@link #build()} is called.
   */
  public static DirectFieldAccessBuilder withSupplier(final Supplier<DirectFieldAccess> supplier) {
    return new DirectFieldAccessBuilder(supplier);
  }

  /**
   * Returns an inner builder for the collection property {@code packagePrivateFieldWithGetAndAdd} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.packagePrivateFieldWithGetAndAdd()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code packagePrivateFieldWithGetAndAdd}.
   */
  public CollectionPackagePrivateFieldWithGetAndAdd packagePrivateFieldWithGetAndAdd() {
    return new CollectionPackagePrivateFieldWithGetAndAdd();
  }

  /**
   * Returns an inner builder for the collection property {@code protectedFieldWithGetAndAdd} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.protectedFieldWithGetAndAdd()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code protectedFieldWithGetAndAdd}.
   */
  public CollectionProtectedFieldWithGetAndAdd protectedFieldWithGetAndAdd() {
    return new CollectionProtectedFieldWithGetAndAdd();
  }

  /**
   * Returns an inner builder for the collection property {@code publicFieldWithGetAndAdd} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.publicFieldWithGetAndAdd()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code publicFieldWithGetAndAdd}.
   */
  public CollectionPublicFieldWithGetAndAdd publicFieldWithGetAndAdd() {
    return new CollectionPublicFieldWithGetAndAdd();
  }

  /**
   * Returns an inner builder for the collection property {@code publicFinalFieldNoSetter} for chained calls of adding items to it.
   * Can be used like follows:
   * <pre>
   * builder.publicFinalFieldNoSetter()
   *        .add(item1)
   *        .add(item2)
   *        .and()
   *        .build()
   * </pre>
   * @return The inner builder for the collection property {@code publicFinalFieldNoSetter}.
   */
  public CollectionPublicFinalFieldNoSetter publicFinalFieldNoSetter() {
    return new CollectionPublicFinalFieldNoSetter();
  }

  /**
   * Sets the value for the {@code packagePrivateFieldNoSetter} property.
   * To be more precise, this will lead to the field {@link DirectFieldAccess#packagePrivateFieldNoSetter} being modified directly on construction of the object.
   * @param packagePrivateFieldNoSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder packagePrivateFieldNoSetter(
      final int packagePrivateFieldNoSetter) {
    this.fieldValue.packagePrivateFieldNoSetter = packagePrivateFieldNoSetter;
    this.callSetterFor.packagePrivateFieldNoSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code packagePrivateFieldWithGetAndAdd} property.
   * To be more precise, this will lead to {@link DirectFieldAccess#getPackagePrivateFieldWithGetAndAdd()} being called on construction of the object.
   * @param packagePrivateFieldWithGetAndAdd the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder packagePrivateFieldWithGetAndAdd(
      final List<String> packagePrivateFieldWithGetAndAdd) {
    this.fieldValue.packagePrivateFieldWithGetAndAdd = packagePrivateFieldWithGetAndAdd;
    this.callSetterFor.packagePrivateFieldWithGetAndAdd = true;
    return this;
  }

  /**
   * Sets the value for the {@code packagePrivateFieldWithSetter} property.
   * To be more precise, this will lead to {@link DirectFieldAccess#setPackagePrivateFieldWithSetter(int)} being called on construction of the object.
   * @param packagePrivateFieldWithSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder packagePrivateFieldWithSetter(
      final int packagePrivateFieldWithSetter) {
    this.fieldValue.packagePrivateFieldWithSetter = packagePrivateFieldWithSetter;
    this.callSetterFor.packagePrivateFieldWithSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code protectedFieldNoSetter} property.
   * To be more precise, this will lead to the field {@link DirectFieldAccess#protectedFieldNoSetter} being modified directly on construction of the object.
   * @param protectedFieldNoSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder protectedFieldNoSetter(final int protectedFieldNoSetter) {
    this.fieldValue.protectedFieldNoSetter = protectedFieldNoSetter;
    this.callSetterFor.protectedFieldNoSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code protectedFieldWithGetAndAdd} property.
   * To be more precise, this will lead to {@link DirectFieldAccess#getProtectedFieldWithGetAndAdd()} being called on construction of the object.
   * @param protectedFieldWithGetAndAdd the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder protectedFieldWithGetAndAdd(
      final List<String> protectedFieldWithGetAndAdd) {
    this.fieldValue.protectedFieldWithGetAndAdd = protectedFieldWithGetAndAdd;
    this.callSetterFor.protectedFieldWithGetAndAdd = true;
    return this;
  }

  /**
   * Sets the value for the {@code protectedFieldWithSetter} property.
   * To be more precise, this will lead to {@link DirectFieldAccess#setProtectedFieldWithSetter(int)} being called on construction of the object.
   * @param protectedFieldWithSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder protectedFieldWithSetter(final int protectedFieldWithSetter) {
    this.fieldValue.protectedFieldWithSetter = protectedFieldWithSetter;
    this.callSetterFor.protectedFieldWithSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code publicFieldNoSetter} property.
   * To be more precise, this will lead to the field {@link DirectFieldAccess#publicFieldNoSetter} being modified directly on construction of the object.
   * @param publicFieldNoSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder publicFieldNoSetter(final int publicFieldNoSetter) {
    this.fieldValue.publicFieldNoSetter = publicFieldNoSetter;
    this.callSetterFor.publicFieldNoSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code publicFieldWithGetAndAdd} property.
   * To be more precise, this will lead to {@link DirectFieldAccess#getPublicFieldWithGetAndAdd()} being called on construction of the object.
   * @param publicFieldWithGetAndAdd the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder publicFieldWithGetAndAdd(
      final List<String> publicFieldWithGetAndAdd) {
    this.fieldValue.publicFieldWithGetAndAdd = publicFieldWithGetAndAdd;
    this.callSetterFor.publicFieldWithGetAndAdd = true;
    return this;
  }

  /**
   * Sets the value for the {@code publicFieldWithPrivateSetter} property.
   * To be more precise, this will lead to the field {@link DirectFieldAccess#publicFieldWithPrivateSetter} being modified directly on construction of the object.
   * @param publicFieldWithPrivateSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder publicFieldWithPrivateSetter(
      final int publicFieldWithPrivateSetter) {
    this.fieldValue.publicFieldWithPrivateSetter = publicFieldWithPrivateSetter;
    this.callSetterFor.publicFieldWithPrivateSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code publicFieldWithSetter} property.
   * To be more precise, this will lead to {@link DirectFieldAccess#setPublicFieldWithSetter(int)} being called on construction of the object.
   * @param publicFieldWithSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder publicFieldWithSetter(final int publicFieldWithSetter) {
    this.fieldValue.publicFieldWithSetter = publicFieldWithSetter;
    this.callSetterFor.publicFieldWithSetter = true;
    return this;
  }

  /**
   * Sets the value for the {@code publicFinalFieldNoSetter} property.
   * To be more precise, this will lead to the field {@link DirectFieldAccess#publicFinalFieldNoSetter} being modified directly on construction of the object.
   * @param publicFinalFieldNoSetter the value to set.
   * @return This builder for chained calls.
   */
  public DirectFieldAccessBuilder publicFinalFieldNoSetter(
      final List<String> publicFinalFieldNoSetter) {
    this.fieldValue.publicFinalFieldNoSetter = publicFinalFieldNoSetter;
    this.callSetterFor.publicFinalFieldNoSetter = true;
    return this;
  }

  /**
   * Performs the actual construction of an instance for {@link DirectFieldAccess}.
   * @return The constructed instance. Never {@code null}.
   */
  public DirectFieldAccess build() {
    final DirectFieldAccess objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.packagePrivateFieldNoSetter) {
      objectToBuild.packagePrivateFieldNoSetter = this.fieldValue.packagePrivateFieldNoSetter;
    }
    if (this.callSetterFor.packagePrivateFieldWithGetAndAdd && this.fieldValue.packagePrivateFieldWithGetAndAdd != null) {
      this.fieldValue.packagePrivateFieldWithGetAndAdd.forEach(objectToBuild.getPackagePrivateFieldWithGetAndAdd()::add);
    }
    if (this.callSetterFor.packagePrivateFieldWithSetter) {
      objectToBuild.setPackagePrivateFieldWithSetter(this.fieldValue.packagePrivateFieldWithSetter);
    }
    if (this.callSetterFor.protectedFieldNoSetter) {
      objectToBuild.protectedFieldNoSetter = this.fieldValue.protectedFieldNoSetter;
    }
    if (this.callSetterFor.protectedFieldWithGetAndAdd && this.fieldValue.protectedFieldWithGetAndAdd != null) {
      this.fieldValue.protectedFieldWithGetAndAdd.forEach(objectToBuild.getProtectedFieldWithGetAndAdd()::add);
    }
    if (this.callSetterFor.protectedFieldWithSetter) {
      objectToBuild.setProtectedFieldWithSetter(this.fieldValue.protectedFieldWithSetter);
    }
    if (this.callSetterFor.publicFieldNoSetter) {
      objectToBuild.publicFieldNoSetter = this.fieldValue.publicFieldNoSetter;
    }
    if (this.callSetterFor.publicFieldWithGetAndAdd && this.fieldValue.publicFieldWithGetAndAdd != null) {
      this.fieldValue.publicFieldWithGetAndAdd.forEach(objectToBuild.getPublicFieldWithGetAndAdd()::add);
    }
    if (this.callSetterFor.publicFieldWithPrivateSetter) {
      objectToBuild.publicFieldWithPrivateSetter = this.fieldValue.publicFieldWithPrivateSetter;
    }
    if (this.callSetterFor.publicFieldWithSetter) {
      objectToBuild.setPublicFieldWithSetter(this.fieldValue.publicFieldWithSetter);
    }
    if (this.callSetterFor.publicFinalFieldNoSetter && this.fieldValue.publicFinalFieldNoSetter != null) {
      this.fieldValue.publicFinalFieldNoSetter.forEach(objectToBuild.publicFinalFieldNoSetter::add);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean packagePrivateFieldNoSetter;

    boolean packagePrivateFieldWithGetAndAdd;

    boolean packagePrivateFieldWithSetter;

    boolean protectedFieldNoSetter;

    boolean protectedFieldWithGetAndAdd;

    boolean protectedFieldWithSetter;

    boolean publicFieldNoSetter;

    boolean publicFieldWithGetAndAdd;

    boolean publicFieldWithPrivateSetter;

    boolean publicFieldWithSetter;

    boolean publicFinalFieldNoSetter;
  }

  private class FieldValue {
    int packagePrivateFieldNoSetter;

    List<String> packagePrivateFieldWithGetAndAdd;

    int packagePrivateFieldWithSetter;

    int protectedFieldNoSetter;

    List<String> protectedFieldWithGetAndAdd;

    int protectedFieldWithSetter;

    int publicFieldNoSetter;

    List<String> publicFieldWithGetAndAdd;

    int publicFieldWithPrivateSetter;

    int publicFieldWithSetter;

    List<String> publicFinalFieldNoSetter;
  }

  public class CollectionPackagePrivateFieldWithGetAndAdd {
    /**
     * Adds an item to the collection property {@code packagePrivateFieldWithGetAndAdd}.
     * @param item The item to add to the collection {@code packagePrivateFieldWithGetAndAdd}.
     * @return This builder for chained calls.
     */
    public CollectionPackagePrivateFieldWithGetAndAdd add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.packagePrivateFieldWithGetAndAdd == null) {
        DirectFieldAccessBuilder.this.fieldValue.packagePrivateFieldWithGetAndAdd = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.packagePrivateFieldWithGetAndAdd.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.packagePrivateFieldWithGetAndAdd = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }

  public class CollectionProtectedFieldWithGetAndAdd {
    /**
     * Adds an item to the collection property {@code protectedFieldWithGetAndAdd}.
     * @param item The item to add to the collection {@code protectedFieldWithGetAndAdd}.
     * @return This builder for chained calls.
     */
    public CollectionProtectedFieldWithGetAndAdd add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.protectedFieldWithGetAndAdd == null) {
        DirectFieldAccessBuilder.this.fieldValue.protectedFieldWithGetAndAdd = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.protectedFieldWithGetAndAdd.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.protectedFieldWithGetAndAdd = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }

  public class CollectionPublicFieldWithGetAndAdd {
    /**
     * Adds an item to the collection property {@code publicFieldWithGetAndAdd}.
     * @param item The item to add to the collection {@code publicFieldWithGetAndAdd}.
     * @return This builder for chained calls.
     */
    public CollectionPublicFieldWithGetAndAdd add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.publicFieldWithGetAndAdd == null) {
        DirectFieldAccessBuilder.this.fieldValue.publicFieldWithGetAndAdd = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.publicFieldWithGetAndAdd.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.publicFieldWithGetAndAdd = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }

  public class CollectionPublicFinalFieldNoSetter {
    /**
     * Adds an item to the collection property {@code publicFinalFieldNoSetter}.
     * @param item The item to add to the collection {@code publicFinalFieldNoSetter}.
     * @return This builder for chained calls.
     */
    public CollectionPublicFinalFieldNoSetter add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.publicFinalFieldNoSetter == null) {
        DirectFieldAccessBuilder.this.fieldValue.publicFinalFieldNoSetter = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.publicFinalFieldNoSetter.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.publicFinalFieldNoSetter = true;
      return this;
    }

    /**
     * Returns the builder for the parent object.
     * @return The builder for the parent object.
     */
    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }
}
