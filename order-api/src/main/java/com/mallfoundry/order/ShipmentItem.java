package com.mallfoundry.order;

public interface ShipmentItem {

    String getId();

    String getStoreId();

    String getProductId();

    String getVariantId();

    String getImageUrl();

    String getTitle();

    int getQuantity();
}
