package org.mallfoundry.catalog.product;

import org.mallfoundry.inventory.InventoryStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MutableProduct extends Product {

    void setFreeShipping(boolean freeShipping);

    void setInventoryQuantity(int quantity);

    void setInventoryStatus(InventoryStatus status);

    void setPrice(BigDecimal price);

    void setOptions(List<ProductOption> options);

    void setVariants(List<ProductVariant> variants);

    void setTotalSales(long sales);

    void setMonthlySales(long sales);

    void setImageUrls(List<String> imageUrls);

    void setVideoUrls(List<String> videoUrls);

    void setCreatedTime(Date createdTime);
}
