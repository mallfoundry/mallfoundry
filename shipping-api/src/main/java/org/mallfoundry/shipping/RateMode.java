package org.mallfoundry.shipping;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RateMode {
    PACKAGE,
    WEIGHT;

    @JsonValue
    private String lowercase() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return this.lowercase();
    }
}
