package org.mallfoundry.order;

public interface Parcel {

    String getId();

    String getStoreId();

    String getProductId();

    String getVariantId();

    String getImageUrl();

    String getTitle();

    int getQuantity();
}
