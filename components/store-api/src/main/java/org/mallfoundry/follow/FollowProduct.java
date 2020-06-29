package org.mallfoundry.follow;

import org.mallfoundry.catalog.product.ProductStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface FollowProduct extends Serializable {

    String getId();

    String getName();

    BigDecimal getPrice();

    String getImageUrl();

    String getStoreId();

    ProductStatus getStatus();

    Date getFollowTime();
}
