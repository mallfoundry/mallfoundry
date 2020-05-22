package com.mallfoundry.store.product;

import com.mallfoundry.data.SliceList;

import java.util.Optional;

public interface ProductService {

    ProductQuery createProductQuery();

    ProductId createProductId(String id);

    Product createProduct();

    ProductVariant createProductVariant();

    Product saveProduct(Product product);

    Optional<Product> getProduct(String id);

    SliceList<Product> getProducts(ProductQuery query);

}
