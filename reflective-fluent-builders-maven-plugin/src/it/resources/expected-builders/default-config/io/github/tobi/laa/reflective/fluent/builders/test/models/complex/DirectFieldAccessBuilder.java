package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;

@Generated(
    value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class DirectFieldAccessBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<DirectFieldAccess> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected DirectFieldAccessBuilder(final Supplier<DirectFieldAccess> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static DirectFieldAccessBuilder newInstance() {
    return new DirectFieldAccessBuilder(DirectFieldAccess::new);
  }

  public static DirectFieldAccessBuilder withSupplier(final Supplier<DirectFieldAccess> supplier) {
    return new DirectFieldAccessBuilder(supplier);
  }

  public CollectionPackagePrivateFieldWithGetAndAdd packagePrivateFieldWithGetAndAdd() {
    return new CollectionPackagePrivateFieldWithGetAndAdd();
  }

  public CollectionProtectedFieldWithGetAndAdd protectedFieldWithGetAndAdd() {
    return new CollectionProtectedFieldWithGetAndAdd();
  }

  public CollectionPublicFieldWithGetAndAdd publicFieldWithGetAndAdd() {
    return new CollectionPublicFieldWithGetAndAdd();
  }

  public CollectionPublicFinalFieldNoSetter publicFinalFieldNoSetter() {
    return new CollectionPublicFinalFieldNoSetter();
  }

  public DirectFieldAccessBuilder packagePrivateFieldNoSetter(
      final int packagePrivateFieldNoSetter) {
    this.fieldValue.packagePrivateFieldNoSetter = packagePrivateFieldNoSetter;
    this.callSetterFor.packagePrivateFieldNoSetter = true;
    return this;
  }

  public DirectFieldAccessBuilder packagePrivateFieldWithGetAndAdd(
      final List<String> packagePrivateFieldWithGetAndAdd) {
    this.fieldValue.packagePrivateFieldWithGetAndAdd = packagePrivateFieldWithGetAndAdd;
    this.callSetterFor.packagePrivateFieldWithGetAndAdd = true;
    return this;
  }

  public DirectFieldAccessBuilder packagePrivateFieldWithSetter(
      final int packagePrivateFieldWithSetter) {
    this.fieldValue.packagePrivateFieldWithSetter = packagePrivateFieldWithSetter;
    this.callSetterFor.packagePrivateFieldWithSetter = true;
    return this;
  }

  public DirectFieldAccessBuilder protectedFieldNoSetter(final int protectedFieldNoSetter) {
    this.fieldValue.protectedFieldNoSetter = protectedFieldNoSetter;
    this.callSetterFor.protectedFieldNoSetter = true;
    return this;
  }

  public DirectFieldAccessBuilder protectedFieldWithGetAndAdd(
      final List<String> protectedFieldWithGetAndAdd) {
    this.fieldValue.protectedFieldWithGetAndAdd = protectedFieldWithGetAndAdd;
    this.callSetterFor.protectedFieldWithGetAndAdd = true;
    return this;
  }

  public DirectFieldAccessBuilder protectedFieldWithSetter(final int protectedFieldWithSetter) {
    this.fieldValue.protectedFieldWithSetter = protectedFieldWithSetter;
    this.callSetterFor.protectedFieldWithSetter = true;
    return this;
  }

  public DirectFieldAccessBuilder publicFieldNoSetter(final int publicFieldNoSetter) {
    this.fieldValue.publicFieldNoSetter = publicFieldNoSetter;
    this.callSetterFor.publicFieldNoSetter = true;
    return this;
  }

  public DirectFieldAccessBuilder publicFieldWithGetAndAdd(
      final List<String> publicFieldWithGetAndAdd) {
    this.fieldValue.publicFieldWithGetAndAdd = publicFieldWithGetAndAdd;
    this.callSetterFor.publicFieldWithGetAndAdd = true;
    return this;
  }

  public DirectFieldAccessBuilder publicFieldWithPrivateSetter(
      final int publicFieldWithPrivateSetter) {
    this.fieldValue.publicFieldWithPrivateSetter = publicFieldWithPrivateSetter;
    this.callSetterFor.publicFieldWithPrivateSetter = true;
    return this;
  }

  public DirectFieldAccessBuilder publicFieldWithSetter(final int publicFieldWithSetter) {
    this.fieldValue.publicFieldWithSetter = publicFieldWithSetter;
    this.callSetterFor.publicFieldWithSetter = true;
    return this;
  }

  public DirectFieldAccessBuilder publicFinalFieldNoSetter(
      final List<String> publicFinalFieldNoSetter) {
    this.fieldValue.publicFinalFieldNoSetter = publicFinalFieldNoSetter;
    this.callSetterFor.publicFinalFieldNoSetter = true;
    return this;
  }

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
    public CollectionPackagePrivateFieldWithGetAndAdd add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.packagePrivateFieldWithGetAndAdd == null) {
        DirectFieldAccessBuilder.this.fieldValue.packagePrivateFieldWithGetAndAdd = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.packagePrivateFieldWithGetAndAdd.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.packagePrivateFieldWithGetAndAdd = true;
      return this;
    }

    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }

  public class CollectionProtectedFieldWithGetAndAdd {
    public CollectionProtectedFieldWithGetAndAdd add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.protectedFieldWithGetAndAdd == null) {
        DirectFieldAccessBuilder.this.fieldValue.protectedFieldWithGetAndAdd = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.protectedFieldWithGetAndAdd.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.protectedFieldWithGetAndAdd = true;
      return this;
    }

    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }

  public class CollectionPublicFieldWithGetAndAdd {
    public CollectionPublicFieldWithGetAndAdd add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.publicFieldWithGetAndAdd == null) {
        DirectFieldAccessBuilder.this.fieldValue.publicFieldWithGetAndAdd = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.publicFieldWithGetAndAdd.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.publicFieldWithGetAndAdd = true;
      return this;
    }

    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }

  public class CollectionPublicFinalFieldNoSetter {
    public CollectionPublicFinalFieldNoSetter add(final String item) {
      if (DirectFieldAccessBuilder.this.fieldValue.publicFinalFieldNoSetter == null) {
        DirectFieldAccessBuilder.this.fieldValue.publicFinalFieldNoSetter = new ArrayList<>();
      }
      DirectFieldAccessBuilder.this.fieldValue.publicFinalFieldNoSetter.add(item);
      DirectFieldAccessBuilder.this.callSetterFor.publicFinalFieldNoSetter = true;
      return this;
    }

    public DirectFieldAccessBuilder and() {
      return DirectFieldAccessBuilder.this;
    }
  }
}
