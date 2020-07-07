package org.mallfoundry.order;

import java.util.List;

public class ImmutableOrdersPlacedEvent extends OrdersEventSupport implements OrdersPlacedEvent {
    public ImmutableOrdersPlacedEvent(List<Order> orders) {
        super(orders);
    }
}
