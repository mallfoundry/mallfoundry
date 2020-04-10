package com.mallfoundry.order;

import com.mallfoundry.data.Pageable;

import java.util.List;
import java.util.function.Supplier;

public interface OrderQuery extends Pageable {

    String getTitle();

    void setTitle(String title);

    List<OrderStatus> getStatuses();

    void setStatuses(List<OrderStatus> statuses);

    String getStoreId();

    void setStoreId(String storeId);

    String getCustomerId();

    void setCustomerId(String customerId);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private OrderQuery query;

        public Builder(OrderQuery query) {
            this.query = query;
        }

        public Builder page(int page) {
            this.query.setPage(page);
            return this;
        }

        public Builder limit(int limit) {
            this.query.setLimit(limit);
            return this;
        }

        public Builder customerId(String customerId) {
            this.query.setCustomerId(customerId);
            return this;
        }

        public Builder storeId(String storeId) {
            this.query.setStoreId(storeId);
            return this;
        }

        public Builder statuses(Supplier<List<OrderStatus>> supplier) {
            return this.statuses(supplier.get());
        }

        public Builder statuses(List<OrderStatus> statuses) {
            this.query.setStatuses(statuses);
            return this;
        }

        public OrderQuery build() {
            return this.query;
        }
    }
}
