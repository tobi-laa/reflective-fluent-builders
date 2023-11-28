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
public class GetAndAddBuilder {
  /**
   * This field is solely used to be able to detect generated builders via reflection at a later stage.
   */
  @SuppressWarnings("unused")
  private boolean ______generatedByReflectiveFluentBuildersGenerator;

  private final Supplier<GetAndAdd> objectSupplier;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  protected GetAndAddBuilder(final Supplier<GetAndAdd> objectSupplier) {
    this.objectSupplier = Objects.requireNonNull(objectSupplier);
  }

  public static GetAndAddBuilder newInstance() {
    return new GetAndAddBuilder(GetAndAdd::new);
  }

  public static GetAndAddBuilder withSupplier(final Supplier<GetAndAdd> supplier) {
    return new GetAndAddBuilder(supplier);
  }

  public ArrayListSetterWrongType listSetterWrongType() {
    return new ArrayListSetterWrongType();
  }

  public CollectionListGetterAndSetter listGetterAndSetter() {
    return new CollectionListGetterAndSetter();
  }

  public CollectionListNoGetter listNoGetter() {
    return new CollectionListNoGetter();
  }

  public CollectionListNoSetter listNoSetter() {
    return new CollectionListNoSetter();
  }

  public CollectionListSetterWrongType0 listSetterWrongType0() {
    return new CollectionListSetterWrongType0();
  }

  public GetAndAddBuilder listGetterAndSetter(final List<String> listGetterAndSetter) {
    this.fieldValue.listGetterAndSetter = listGetterAndSetter;
    this.callSetterFor.listGetterAndSetter = true;
    return this;
  }

  public GetAndAddBuilder listNoGetter(final List<String> listNoGetter) {
    this.fieldValue.listNoGetter = listNoGetter;
    this.callSetterFor.listNoGetter = true;
    return this;
  }

  public GetAndAddBuilder listNoSetter(final List<String> listNoSetter) {
    this.fieldValue.listNoSetter = listNoSetter;
    this.callSetterFor.listNoSetter = true;
    return this;
  }

  public GetAndAddBuilder listSetterWrongType(final String[] listSetterWrongType) {
    this.fieldValue.listSetterWrongType = listSetterWrongType;
    this.callSetterFor.listSetterWrongType = true;
    return this;
  }

  public GetAndAddBuilder listSetterWrongType(final List<String> listSetterWrongType0) {
    this.fieldValue.listSetterWrongType0 = listSetterWrongType0;
    this.callSetterFor.listSetterWrongType0 = true;
    return this;
  }

  public GetAndAdd build() {
    final GetAndAdd objectToBuild = this.objectSupplier.get();
    if (this.callSetterFor.listGetterAndSetter) {
      objectToBuild.setListGetterAndSetter(this.fieldValue.listGetterAndSetter);
    }
    if (this.callSetterFor.listNoGetter) {
      objectToBuild.setListNoGetter(this.fieldValue.listNoGetter);
    }
    if (this.callSetterFor.listNoSetter && this.fieldValue.listNoSetter != null) {
      this.fieldValue.listNoSetter.forEach(objectToBuild.getListNoSetter()::add);
    }
    if (this.callSetterFor.listSetterWrongType) {
      objectToBuild.setListSetterWrongType(this.fieldValue.listSetterWrongType);
    }
    if (this.callSetterFor.listSetterWrongType0 && this.fieldValue.listSetterWrongType0 != null) {
      this.fieldValue.listSetterWrongType0.forEach(objectToBuild.getListSetterWrongType()::add);
    }
    return objectToBuild;
  }

  private class CallSetterFor {
    boolean listGetterAndSetter;

    boolean listNoGetter;

    boolean listNoSetter;

    boolean listSetterWrongType;

    boolean listSetterWrongType0;
  }

  private class FieldValue {
    List<String> listGetterAndSetter;

    List<String> listNoGetter;

    List<String> listNoSetter;

    String[] listSetterWrongType;

    List<String> listSetterWrongType0;
  }

  public class ArrayListSetterWrongType {
    private List<String> list;

    public ArrayListSetterWrongType add(final String item) {
      if (this.list == null) {
        this.list = new ArrayList<>();
      }
      this.list.add(item);
      GetAndAddBuilder.this.callSetterFor.listSetterWrongType = true;
      return this;
    }

    public GetAndAddBuilder and() {
      if (this.list != null) {
        GetAndAddBuilder.this.fieldValue.listSetterWrongType = new String[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
          GetAndAddBuilder.this.fieldValue.listSetterWrongType[i] = this.list.get(i);
        }
      }
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListGetterAndSetter {
    public CollectionListGetterAndSetter add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listGetterAndSetter == null) {
        GetAndAddBuilder.this.fieldValue.listGetterAndSetter = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listGetterAndSetter.add(item);
      GetAndAddBuilder.this.callSetterFor.listGetterAndSetter = true;
      return this;
    }

    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListNoGetter {
    public CollectionListNoGetter add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listNoGetter == null) {
        GetAndAddBuilder.this.fieldValue.listNoGetter = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listNoGetter.add(item);
      GetAndAddBuilder.this.callSetterFor.listNoGetter = true;
      return this;
    }

    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListNoSetter {
    public CollectionListNoSetter add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listNoSetter == null) {
        GetAndAddBuilder.this.fieldValue.listNoSetter = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listNoSetter.add(item);
      GetAndAddBuilder.this.callSetterFor.listNoSetter = true;
      return this;
    }

    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }

  public class CollectionListSetterWrongType0 {
    public CollectionListSetterWrongType0 add(final String item) {
      if (GetAndAddBuilder.this.fieldValue.listSetterWrongType0 == null) {
        GetAndAddBuilder.this.fieldValue.listSetterWrongType0 = new ArrayList<>();
      }
      GetAndAddBuilder.this.fieldValue.listSetterWrongType0.add(item);
      GetAndAddBuilder.this.callSetterFor.listSetterWrongType0 = true;
      return this;
    }

    public GetAndAddBuilder and() {
      return GetAndAddBuilder.this;
    }
  }
}
