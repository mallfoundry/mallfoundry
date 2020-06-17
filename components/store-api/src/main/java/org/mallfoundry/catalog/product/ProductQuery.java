package org.mallfoundry.catalog.product;

import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;
import org.mallfoundry.inventory.InventoryStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Supplier;

public interface ProductQuery extends Query {

    Set<String> getIds();

    void setIds(Set<String> ids);

    String getName();

    void setName(String name);

    String getStoreId();

    void setStoreId(String storeId);

    BigDecimal getMinPrice();

    void setMinPrice(BigDecimal minPrice);

    BigDecimal getMaxPrice();

    void setMaxPrice(BigDecimal maxPrice);

    Set<String> getCollections();

    void setCollections(Set<String> collections);

    Set<ProductType> getTypes();

    void setTypes(Set<ProductType> types);

    Set<ProductStatus> getStatuses();

    void setStatuses(Set<ProductStatus> statuses);

    Set<InventoryStatus> getInventoryStatuses();

    void setInventoryStatuses(Set<InventoryStatus> statuses);

    Builder toBuilder();

    interface Builder extends QueryBuilder<ProductQuery, Builder> {
        Builder ids(Set<String> ids);

        Builder name(String name);

        Builder minPrice(BigDecimal minPrice);

        Builder maxPrice(BigDecimal maxPrice);

        Builder storeId(String storeId);

        Builder collections(Set<String> collections);

        Builder types(Set<ProductType> types);

        Builder types(Supplier<Set<ProductType>> supplier);

        Builder statuses(Set<ProductStatus> statuses);

        Builder statuses(Supplier<Set<ProductStatus>> supplier);

        Builder inventoryStatuses(Set<InventoryStatus> statuses);

        Builder inventoryStatuses(Supplier<Set<InventoryStatus>> supplier);
    }
}
