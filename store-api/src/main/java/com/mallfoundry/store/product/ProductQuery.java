package com.mallfoundry.store.product;

import com.mallfoundry.data.Pageable;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Supplier;

public interface ProductQuery extends Pageable {

    String getName();

    void setName(String name);

    String getStoreId();

    void setStoreId(String storeId);

    BigDecimal getMinPrice();

    void setMinPrice(BigDecimal minPrice);

    BigDecimal getMaxPrice();

    void setMaxPrice(BigDecimal maxPrice);

    Set<String> getCollectionIds();

    void setCollectionIds(Set<String> collectionIds);

    Set<ProductType> getTypes();

    void setTypes(Set<ProductType> types);

    Set<ProductStatus> getStatuses();

    void setStatuses(Set<ProductStatus> statuses);

    Set<InventoryStatus> getInventoryStatuses();

    void setInventoryStatuses(Set<InventoryStatus> statuses);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final ProductQuery query;

        public Builder(ProductQuery query) {
            this.query = query;
        }

        public Builder page(Integer page) {
            this.query.setPage(page);
            return this;
        }

        public Builder limit(Integer limit) {
            this.query.setLimit(limit);
            return this;
        }

//        public Builder productId(String productId) {
//            this.query.setProductId(productId);
//            return this;
//        }

        public Builder name(String name) {
            this.query.setName(name);
            return this;
        }

        public Builder minPrice(BigDecimal minPrice) {
            this.query.setMinPrice(minPrice);
            return this;
        }

        public Builder maxPrice(BigDecimal maxPrice) {
            this.query.setMaxPrice(maxPrice);
            return this;
        }

        public Builder storeId(String storeId) {
            this.query.setStoreId(storeId);
            return this;
        }

        public Builder collectionIds(Set<String> collectionIds) {
            this.query.setCollectionIds(collectionIds);
            return this;
        }

        public Builder types(Set<ProductType> types) {
            this.query.setTypes(types);
            return this;
        }

        public Builder types(Supplier<Set<ProductType>> supplier) {
            return this.types(supplier.get());
        }

        public Builder statuses(Set<ProductStatus> statuses) {
            this.query.setStatuses(statuses);
            return this;
        }

        public Builder statuses(Supplier<Set<ProductStatus>> supplier) {
            return this.statuses(supplier.get());
        }

        public Builder inventoryStatuses(Set<InventoryStatus> statuses) {
            this.query.setInventoryStatuses(statuses);
            return this;
        }

        public Builder inventoryStatuses(Supplier<Set<InventoryStatus>> supplier) {
            return this.inventoryStatuses(supplier.get());
        }

        public ProductQuery build() {
            return this.query;
        }
    }
}
