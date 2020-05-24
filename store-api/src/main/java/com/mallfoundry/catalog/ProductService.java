package com.mallfoundry.catalog;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.inventory.InventoryAdjustment;

import java.util.Optional;

public interface ProductService {

    ProductQuery createProductQuery();

    ProductId createProductId(String id);

    Product createProduct();


    Optional<Product> getProduct(String id);

    SliceList<Product> getProducts(ProductQuery query);

    Product saveProduct(Product product);

    void adjustInventory(InventoryAdjustment adjustment);
}
