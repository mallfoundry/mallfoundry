package com.mallfoundry.catalog;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductType {
    PHYSICAL, DIGITAL;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
