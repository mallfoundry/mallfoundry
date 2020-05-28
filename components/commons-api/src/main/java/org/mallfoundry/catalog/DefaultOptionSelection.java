package org.mallfoundry.catalog;

import lombok.NoArgsConstructor;

/**
 * @author Tang Zhi
 * @since 1.0
 */
@NoArgsConstructor
public class DefaultOptionSelection extends OptionSelectionSupport {

    public DefaultOptionSelection(String nameId, String name, String valueId, String value) {
        this.setNameId(nameId);
        this.setName(name);
        this.setValueId(valueId);
        this.setValue(value);
    }
}
