package com.mallfoundry.catalog;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultOptionSelection extends OptionSelectionSupport {

    public DefaultOptionSelection(String nameId, String name, String valueId, String value) {
        this.setNameId(nameId);
        this.setName(name);
        this.setValueId(valueId);
        this.setValue(value);
    }
}
