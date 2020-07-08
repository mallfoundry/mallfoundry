package org.mallfoundry.order;

public class ImmutableOrderPlacedEvent extends OrderEventSupport implements OrderPlacedEvent {

    protected ImmutableOrderPlacedEvent(Order order) {
        super(order);
    }
}
