package org.mallfoundry.inventory;

public interface InventoryService {

    InventoryAdjustment createInventoryAdjustment();

    void adjustInventory(InventoryAdjustment adjustment);

}
