package org.mallfoundry.inventory;

public interface InventoryAdjustedEvent extends InventoryEvent {

    InventoryAdjustment getInventoryAdjustment();
}
