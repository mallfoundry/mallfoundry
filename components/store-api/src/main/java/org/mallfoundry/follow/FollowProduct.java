package org.mallfoundry.follow;

import org.mallfoundry.catalog.ProductStatus;

import java.io.Serializable;
import java.util.Date;

public interface FollowProduct extends Serializable {

    String getId();

    String getName();

    String getImageUrl();

    ProductStatus getStatus();

    Date getFollowTime();
}
