package com.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.processing.Generated;

@Generated(
    value = "com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
    date = "3333-03-13T00:00Z[UTC]"
)
public class GetAndAddBuilder {
  private GetAndAdd objectToBuild;

  private final CallSetterFor callSetterFor = new CallSetterFor();

  private final FieldValue fieldValue = new FieldValue();

  private GetAndAddBuilder(final GetAndAdd objectToBuild) {
    this.objectToBuild = objectToBuild;
  }

  public static GetAndAddBuilder newInstance() {
    return new GetAndAddBuilder(null);
  }

  public static GetAndAddBuilder thatModifies(final GetAndAdd objectToModify) {
    Objects.requireNonNull(objectToModify);
    return new GetAndAddBuilder(objectToModify);
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

  public CollectionListSetterWrongType0 listSetterWrongType() {
    return new CollectionListSetterWrongType0();
  }

  public GetAndAddBuilder listGetterAndSetter(final List<String> listGetterAndSetter) {
    fieldValue.listGetterAndSetter = listGetterAndSetter;
    callSetterFor.listGetterAndSetter = true;
    return this;
  }

  public GetAndAddBuilder listNoGetter(final List<String> listNoGetter) {
    fieldValue.listNoGetter = listNoGetter;
    callSetterFor.listNoGetter = true;
    return this;
  }

  public GetAndAddBuilder listNoSetter(final List<String> listNoSetter) {
    fieldValue.listNoSetter = listNoSetter;
    callSetterFor.listNoSetter = true;
    return this;
  }

  public GetAndAddBuilder listSetterWrongType(final String[] listSetterWrongType) {
    fieldValue.listSetterWrongType = listSetterWrongType;
    callSetterFor.listSetterWrongType = true;
    return this;
  }

  public GetAndAddBuilder listSetterWrongType(final List<String> listSetterWrongType0) {
    fieldValue.listSetterWrongType0 = listSetterWrongType0;
    callSetterFor.listSetterWrongType0 = true;
    return this;
  }

  public GetAndAdd build() {
    if (objectToBuild == null) {
      objectToBuild = new GetAndAdd();
    }
    if (callSetterFor.listGetterAndSetter) {
      objectToBuild.setListGetterAndSetter(fieldValue.listGetterAndSetter);
    }
    if (callSetterFor.listNoGetter) {
      objectToBuild.setListNoGetter(fieldValue.listNoGetter);
    }
    if (callSetterFor.listNoSetter && fieldValue.listNoSetter != null) {
      fieldValue.listNoSetter.forEach(objectToBuild.getListNoSetter()::add);
    }
    if (callSetterFor.listSetterWrongType) {
      objectToBuild.setListSetterWrongType(fieldValue.listSetterWrongType);
    }
    if (callSetterFor.listSetterWrongType0 && fieldValue.listSetterWrongType0 != null) {
      fieldValue.listSetterWrongType0.forEach(objectToBuild.getListSetterWrongType()::add);
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
