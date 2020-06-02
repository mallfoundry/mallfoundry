package org.mallfoundry.catalog;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductQuery createProductQuery();

    ProductId createProductId(String id);

    Product createProduct(String id);

    Optional<Product> getProduct(String id);

    SliceList<Product> getProducts(ProductQuery query);

    Product addProduct(Product product);

    void updateProduct(Product product);

    void adjustInventory(InventoryAdjustment adjustment);

    void adjustInventories(List<InventoryAdjustment> adjustments);
}
