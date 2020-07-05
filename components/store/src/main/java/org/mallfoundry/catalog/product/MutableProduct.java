package org.mallfoundry.catalog.product;

import org.mallfoundry.inventory.InventoryStatus;

import java.util.Date;
import java.util.List;

public interface MutableProduct extends Product {

    void setFreeShipping(boolean freeShipping);

    void setInventoryStatus(InventoryStatus status);

    void setOptions(List<ProductOption> options);

    void setVariants(List<ProductVariant> variants);

    void setCreatedTime(Date createdTime);

    void setTotalSales(long sales);

    void setMonthlySales(long sales);

    void setVersion(long version);

    void setImageUrls(List<String> imageUrls);

    void setVideoUrls(List<String> videoUrls);
}
