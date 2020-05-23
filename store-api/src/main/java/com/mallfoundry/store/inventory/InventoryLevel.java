package com.mallfoundry.store.inventory;

public interface InventoryLevel {

    String getInventoryItemId();

    String getLocationId();

    int getAvailable();
}
