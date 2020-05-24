package com.mallfoundry.catalog.product;

import com.mallfoundry.catalog.product.OptionSelection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OptionSelectionSupport implements OptionSelection {

    private String name;

    private String value;
}
