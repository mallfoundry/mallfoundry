package org.mallfoundry.security.acl;

import java.io.Serializable;

/**
 * 访问控制列表中所表示的资源对象是一种受限资源对象。
 * 将一个权限对一种受限资源对象授权给一个主体后，这个主体才有权访问这个资源对象。
 *
 * @author Zhi Tang
 * @see Principal
 * @see Permission
 */
public interface Resource extends Serializable {

    String STORE_TYPE = "Store";

    String PRODUCT_TYPE = "Product";

    String CUSTOMER_TYPE = "Customer";

    String ORDER_TYPE = "Order";

    String getIdentifier();

    String getType();
}
