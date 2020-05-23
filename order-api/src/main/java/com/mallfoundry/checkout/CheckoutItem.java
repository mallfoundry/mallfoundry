package com.mallfoundry.checkout;

import java.util.List;

public interface CheckoutItem {

    String getId();

    String getStoreId();

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    List<String> getOptionValues();

    void setOptionValues(List<String> optionValues);

    int getQuantity();

    void setQuantity(int quantity);

}
