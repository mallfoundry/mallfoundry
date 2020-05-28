package org.mallfoundry.catalog;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public enum CategoryVisibility {
    VISIBLE, HIDDEN;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
