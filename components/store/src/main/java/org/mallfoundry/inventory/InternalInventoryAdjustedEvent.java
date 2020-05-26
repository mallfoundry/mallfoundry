package org.mallfoundry.inventory;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class InternalInventoryAdjustedEvent extends ApplicationEvent implements InventoryAdjustedEvent {

    @Getter
    private final InventoryAdjustment inventoryAdjustment;

    public InternalInventoryAdjustedEvent(InventoryAdjustment inventoryAdjustment) {
        super(inventoryAdjustment);
        this.inventoryAdjustment = inventoryAdjustment;
    }
}
