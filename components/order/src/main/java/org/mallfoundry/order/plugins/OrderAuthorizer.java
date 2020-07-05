package org.mallfoundry.order.plugins;

import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderPlugin;
import org.mallfoundry.order.OrderQuery;
import org.mallfoundry.security.acl.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class OrderAuthorizer implements OrderPlugin {

    @PreAuthorize("hasPermission(#query.customerId, '" + Resource.CUSTOMER_TYPE + "', 'order_browse') or "
            + "hasPermission(#query.storeId, '" + Resource.STORE_TYPE + "', 'order_browse')")
    @Override
    public void preGetOrders(OrderQuery query) {
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', 'order_browse') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', 'order_browse')")
    @Override
    public void preGetOrder(Order order) {
        System.out.println(order);
    }
}
