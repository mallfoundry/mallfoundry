package org.mallfoundry.catalog.product;

public interface ProductProcessor {

    default Product processPreAddProduct(Product product) {
        return product;
    }
}
