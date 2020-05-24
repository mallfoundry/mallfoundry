package com.mallfoundry.catalog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OptionSelectionSupport implements OptionSelection {

    private String name;

    private String value;
}
