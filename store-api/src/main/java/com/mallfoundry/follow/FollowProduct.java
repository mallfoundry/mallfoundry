package com.mallfoundry.follow;

import com.mallfoundry.store.product.ProductStatus;

import java.io.Serializable;
import java.util.Date;

public interface FollowProduct extends Serializable {

    String getId();

    String getName();

    String getImageUrl();

    ProductStatus getStatus();

    Date getFollowTime();
}
