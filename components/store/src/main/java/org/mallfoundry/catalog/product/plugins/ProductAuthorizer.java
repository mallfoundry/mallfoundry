package org.mallfoundry.catalog.product.plugins;

import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductPlugin;
import org.mallfoundry.security.acl.Resource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProductAuthorizer implements ProductPlugin {

    // hasPermission(#product.storeId, 'Store', 'product_add')
    @PostAuthorize("hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', 'product_add')")
    @Override
    public void preAddProduct(Product product) {
    }
}
