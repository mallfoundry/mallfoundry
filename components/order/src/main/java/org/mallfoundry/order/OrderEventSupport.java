package org.mallfoundry.order;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public abstract class OrderEventSupport extends ApplicationEvent implements OrderEvent {

    @Getter
    private final Order order;

    protected OrderEventSupport(Order order) {
        super(order);
        this.order = order;
    }
}
