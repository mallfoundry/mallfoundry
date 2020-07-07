package org.mallfoundry.order;

import org.mallfoundry.security.acl.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class OrderAuthorizer implements OrderProcessor {

    @PreAuthorize("hasPermission(#query.customerId, '" + Resource.CUSTOMER_TYPE + "', 'order_browse') or "
            + "hasPermission(#query.storeId, '" + Resource.STORE_TYPE + "', 'order_browse')")
    @Override
    public OrderQuery processPreGetOrders(OrderQuery query) {
        return query;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', 'order_browse') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', 'order_browse')")
    @Override
    public Order processPostGetOrder(Order order) {
        return order;
    }
}
