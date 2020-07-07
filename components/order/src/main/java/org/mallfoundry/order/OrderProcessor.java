package org.mallfoundry.order;

import org.springframework.data.domain.Slice;

public interface OrderProcessor {

    default Order processPostGetOrder(Order order) {
        return order;
    }

    default OrderQuery processPreGetOrders(OrderQuery query) {
        return query;
    }

    default Slice<Order> processPostGetOrders(Slice<Order> orders) {
        return orders;
    }
}
