package com.mallfoundry.rest.store.product;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InventoryStatus {
    IN_STOCK, LIMITED_STOCK, OUT_OF_STOCK;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
