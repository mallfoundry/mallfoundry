package com.mallfoundry.store.product;

import com.mallfoundry.data.Pageable;

import java.util.Set;
import java.util.function.Supplier;

public interface ProductQuery extends Pageable {

    String getName();

    void setName(String name);

    String getStoreId();

    void setStoreId(String storeId);

//    String getProductId();
//
//    void setProductId(String productId);

    Set<String> getCollectionIds();

    void setCollectionIds(Set<String> collectionIds);

    Set<ProductType> getTypes();

    void setTypes(Set<ProductType> types);

    Builder toBuilder();

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

        public Builder storeId(String storeId) {
            this.query.setStoreId(storeId);
            return this;
        }

        public Builder collectionIds(Set<String> collectionIds) {
            this.query.setCollectionIds(collectionIds);
            return this;
        }

        public Builder name(String name) {
            this.query.setName(name);
            return this;
        }

        public Builder types(Set<ProductType> types) {
            this.query.setTypes(types);
            return this;
        }

        public Builder types(Supplier<Set<ProductType>> supplier) {
            return this.types(supplier.get());
        }

        public ProductQuery build() {
            return this.query;
        }
    }
}
