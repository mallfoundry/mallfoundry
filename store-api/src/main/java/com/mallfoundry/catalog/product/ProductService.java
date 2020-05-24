package com.mallfoundry.catalog.product;

import com.mallfoundry.data.SliceList;

import java.util.Optional;

public interface ProductService {

    ProductQuery createProductQuery();

    ProductId createProductId(String id);

    Product createProduct();

    InventoryAdjustment createInventoryAdjustment();

    Optional<Product> getProduct(String id);

    SliceList<Product> getProducts(ProductQuery query);

    Product saveProduct(Product product);

    void adjustInventory(InventoryAdjustment adjustment);
}
