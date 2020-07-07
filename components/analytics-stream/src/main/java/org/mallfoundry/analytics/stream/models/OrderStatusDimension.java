package org.mallfoundry.analytics.stream.models;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.OrderStatus;

@Getter
@Setter
public class OrderStatusDimension {

    private String id;

    private String label;

    public static String idOf(OrderStatus status) {
        return status.toString();
    }
}
