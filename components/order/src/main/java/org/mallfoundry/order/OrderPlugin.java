package org.mallfoundry.order;

import org.mallfoundry.plugins.Plugin;

public interface OrderPlugin extends Plugin<Order> {

    void preGetOrder(Order order);

    void preGetOrders(OrderQuery query);
}
