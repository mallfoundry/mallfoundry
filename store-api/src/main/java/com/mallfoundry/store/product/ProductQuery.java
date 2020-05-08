package com.mallfoundry.store.product;

import com.mallfoundry.data.Pageable;

public interface ProductQuery extends Pageable {

    String getTitle();

    void setTitle(String title);

    String getStoreId();

    void setStoreId(String storeId);

//    String getProductId();
//
//    void setProductId(String productId);

    ProductType getType();

    void setType(ProductType type);

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

        public Builder title(String title) {
            this.query.setTitle(title);
            return this;
        }

        public Builder type(ProductType type) {
            this.query.setType(type);
            return this;
        }

        public ProductQuery build() {
            return this.query;
        }
    }
}
