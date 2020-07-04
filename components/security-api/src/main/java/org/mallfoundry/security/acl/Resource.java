package org.mallfoundry.security.acl;

import java.io.Serializable;

public interface Resource extends Serializable {

    String STORE_TYPE = "Store";

    String PRODUCT_TYPE = "Product";

    String getIdentifier();

    String getType();
}
