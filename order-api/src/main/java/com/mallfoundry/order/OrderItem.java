package com.mallfoundry.order;

import com.mallfoundry.catalog.product.OptionSelection;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItem {

    String getId();

    String getStoreId();

    String getProductId();

    String getVariantId();

    List<OptionSelection> getOptionSelections();

    String getImageUrl();

    void setImageUrl(String imageUrl);

    String getName();

    void setName(String name);

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
