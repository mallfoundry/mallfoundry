package com.mallfoundry.store.inventory;

public interface InventoryService {

    AdjustInventory createAdjustInventory();

    void adjustInventory(AdjustInventory adjustInventory);

}
