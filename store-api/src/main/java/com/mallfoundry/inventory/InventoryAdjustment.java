package com.mallfoundry.inventory;

public interface InventoryAdjustment {

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantityDelta();

    void setQuantityDelta(int quantityDelta);
}
