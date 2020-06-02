package org.mallfoundry.inventory;

import java.util.List;

public interface InventoryService {

    InventoryAdjustment createInventoryAdjustment();

    void adjustInventory(InventoryAdjustment adjustment);

    void adjustInventories(List<InventoryAdjustment> adjustments);
}
