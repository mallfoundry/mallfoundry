package org.mallfoundry.catalog;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tang Zhi
 * @since 1.0
 */
@Getter
@Setter
public abstract class OptionSelectionSupport implements OptionSelection {

    private String nameId;

    private String name;

    private String valueId;

    private String value;
}
