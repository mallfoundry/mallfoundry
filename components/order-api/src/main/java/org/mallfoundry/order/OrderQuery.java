package org.mallfoundry.order;

import org.mallfoundry.data.Query;
import org.mallfoundry.payment.methods.PaymentMethod;

import java.util.Date;
import java.util.Set;
import java.util.function.Supplier;

public interface OrderQuery extends Query {

    Set<String> getIds();

    void setIds(Set<String> ids);

    String getName();

    void setName(String name);

    Set<OrderStatus> getStatuses();

    void setStatuses(Set<OrderStatus> statuses);

    Set<OrderType> getTypes();

    void setTypes(Set<OrderType> types);

    String getStoreId();

    void setStoreId(String storeId);

    String getCustomerId();

    void setCustomerId(String customerId);

    Set<PaymentMethod> getPaymentMethods();

    void setPaymentMethods(Set<PaymentMethod> methods);

    Set<OrderSource> getSources();

    void setSources(Set<OrderSource> sources);

    Date getMinPlacedTime();

    void setMinPlacedTime(Date time);

    Date getMaxPlacedTime();

    void setMaxPlacedTime(Date time);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final OrderQuery query;

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

        public Builder name(String name) {
            this.query.setName(name);
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

        public Builder statuses(Supplier<Set<OrderStatus>> supplier) {
            return this.statuses(supplier.get());
        }

        public Builder statuses(Set<OrderStatus> statuses) {
            this.query.setStatuses(statuses);
            return this;
        }

        public OrderQuery build() {
            return this.query;
        }
    }
}
