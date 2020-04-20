package com.mallfoundry.order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItem {

    String getId();

    String getStoreId();

    String getProductId();

    String getVariantId();

    List<String> getOptionValues();

    String getImageUrl();

    void setImageUrl(String imageUrl);

    String getTitle();

    void setTitle(String title);

    int getQuantity();

    BigDecimal getPrice();

    BigDecimal getShippingCost();

    void setShippingCost(BigDecimal shippingCost);

    BigDecimal getDiscountShippingCost();

    void setDiscountShippingCost(BigDecimal discountShippingCost);

    BigDecimal getSubtotalAmount();

    BigDecimal getOriginalAmount();

    BigDecimal getDiscountAmount();

    void setDiscountAmount(BigDecimal discountAmount);

    BigDecimal getActualAmount();
}
