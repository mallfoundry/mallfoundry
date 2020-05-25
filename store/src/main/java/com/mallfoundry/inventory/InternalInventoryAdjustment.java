package com.mallfoundry.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalInventoryAdjustment implements InventoryAdjustment {

    private String productId;

    private String variantId;

    private int quantityDelta;
}
