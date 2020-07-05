package org.mallfoundry.security.acl;

import java.io.Serializable;

public interface Resource extends Serializable {

    String STORE_TYPE = "Store";

    String PRODUCT_TYPE = "Product";

    String CUSTOMER_TYPE = "Customer";

    String ORDER_TYPE = "Order";

    String getIdentifier();

    String getType();
}
