package com.mallfoundry.store.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Product extends Serializable {

    String getId();

    void setId(String id);

    String getStoreId();

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getMarketPrice();

    void setMarketPrice(BigDecimal marketPrice);

    List<String> getImageUrls();

    List<String> getVideoUrls();

    boolean isFreeShipping();

    void setFreeShipping(boolean freeShipping);

    BigDecimal getFixedShippingCost();

    void setFixedShippingCost(BigDecimal fixedShippingCost);

    String getShippingRateId();

    void setShippingRateId(String shippingRateId);

    Date getCreatedTime();

    List<ProductVariant> getVariants();

    Optional<ProductVariant> getVariant(String variantId);
}
