package org.mallfoundry.catalog;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;

import java.util.Optional;

public interface ProductService {

    ProductQuery createProductQuery();

    ProductId createProductId(String id);

    Product createProduct();

    Optional<Product> getProduct(String id);

    SliceList<Product> getProducts(ProductQuery query);

    Product saveProduct(Product product);

    Optional<ProductVariant> getProductVariant(String productId, String variantId);

    void adjustInventory(InventoryAdjustment adjustment);
}
