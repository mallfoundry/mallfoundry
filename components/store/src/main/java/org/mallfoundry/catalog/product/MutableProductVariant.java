package org.mallfoundry.catalog.product;

import org.mallfoundry.inventory.InventoryException;
import org.mallfoundry.inventory.InventoryStatus;

public interface MutableProductVariant extends ProductVariant {

    void setInventoryQuantity(int quantity) throws InventoryException;

    void setInventoryStatus(InventoryStatus inventoryStatus);
}
