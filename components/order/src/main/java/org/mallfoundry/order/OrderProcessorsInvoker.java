package org.mallfoundry.order;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderProcessorsInvoker {

    private final List<OrderProcessor> processors;

    @Autowired(required = false)
    public OrderProcessorsInvoker(List<OrderProcessor> processors) {
        this.processors = Collections.unmodifiableList(ListUtils.emptyIfNull(processors));
    }

    public Order invokeProcessPostGetOrder(Order order) {
        var result = order;
        for (var processor : this.processors) {
            result = processor.processPostGetOrder(result);
        }
        return result;
    }
}
