package org.mallfoundry.catalog.product;

import java.util.Date;
import java.util.List;

public interface MutableProduct extends Product {

    void setVariants(List<ProductVariant> variants);

    void setCreatedTime(Date createdTime);
}
