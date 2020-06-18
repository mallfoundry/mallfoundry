package org.mallfoundry.catalog.product;

import java.util.Date;
import java.util.List;

public interface MutableProduct extends Product {

    void setOptions(List<ProductOption> options);

    void setVariants(List<ProductVariant> variants);

    void setCreatedTime(Date createdTime);

    void setTotalSales(long sales);

    void setMonthlySales(long sales);
}
