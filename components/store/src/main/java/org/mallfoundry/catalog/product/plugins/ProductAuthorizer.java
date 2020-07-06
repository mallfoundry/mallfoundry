package org.mallfoundry.catalog.product.plugins;

import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductProcessor;
import org.mallfoundry.security.acl.Resource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;

@Component
public class ProductAuthorizer implements ProductProcessor {

    // hasPermission(#product.storeId, 'Store', 'product_add')
//    @PostAuthorize("hasPermission(#product.storeId, '" + Resource.STORE_TYPE + "', 'product_add')")
    @Override
    public Product processPreAddProduct(Product product) {
        return product;
    }
}
