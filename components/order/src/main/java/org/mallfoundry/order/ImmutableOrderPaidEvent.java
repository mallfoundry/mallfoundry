package org.mallfoundry.order;

public class ImmutableOrderPaidEvent extends OrderEventSupport implements OrderPaidEvent {

    protected ImmutableOrderPaidEvent(Order order) {
        super(order);
    }
}
