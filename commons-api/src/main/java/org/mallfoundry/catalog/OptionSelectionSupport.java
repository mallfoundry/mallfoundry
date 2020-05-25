package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OptionSelectionSupport implements OptionSelection {

    private String nameId;

    private String name;

    private String valueId;

    private String value;
}
