package org.mallfoundry.catalog;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryVisibility {
    VISIBLE, HIDDEN;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
