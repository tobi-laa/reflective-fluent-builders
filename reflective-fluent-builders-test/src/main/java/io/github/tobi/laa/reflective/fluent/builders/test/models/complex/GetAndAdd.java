package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class GetAndAdd {

    @Getter
    private Map<String, Object> mapNoSetter;

    @Getter
    @Setter
    private List<String> listGetterAndSetter;

    @Getter
    private List<String> listNoSetter;

    @Setter
    private List<String> listNoGetter;

    @Getter
    private List<String> listSetterWrongType;

    public void setListSetterWrongType(final String... listSetterWrongType) {
        this.listSetterWrongType = List.of(listSetterWrongType);
    }
}
