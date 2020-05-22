package com.mallfoundry.rest.store;

import com.mallfoundry.data.Pageable;

public interface StoreQuery extends Pageable {

    String getOwnerId();

    void setOwnerId(String ownerId);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final StoreQuery query;

        public Builder(StoreQuery query) {
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

        public Builder ownerId(String ownerId) {
            this.query.setOwnerId(ownerId);
            return this;
        }

        public StoreQuery build() {
            return this.query;
        }
    }
}
