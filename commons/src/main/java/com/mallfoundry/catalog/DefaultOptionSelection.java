package com.mallfoundry.catalog;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultOptionSelection extends OptionSelectionSupport {

    public DefaultOptionSelection(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }
}
