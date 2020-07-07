package org.mallfoundry.order;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.util.CastUtils;

import java.util.Collections;
import java.util.List;

public abstract class OrdersEventSupport extends ApplicationEvent implements OrdersEvent {

    @Getter
    private final List<Order> orders;

    public OrdersEventSupport(List<Order> orders) {
        super(Collections.unmodifiableList(orders));
        this.orders = CastUtils.cast(this.getSource());
    }
}
