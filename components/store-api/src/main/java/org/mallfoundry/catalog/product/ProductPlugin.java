package org.mallfoundry.catalog.product;

import org.mallfoundry.plugins.Plugin;

public interface ProductPlugin extends Plugin<Product> {

    default void preAddProduct(Product product) {

    }

    default void preGetProduct(String id) {

    }
}
