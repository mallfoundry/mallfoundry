package com.mallfoundry.catalog.product;

public interface InventoryAdjustment {

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantity();

    void setQuantity(int quantity);
}
