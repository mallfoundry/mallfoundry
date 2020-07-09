package org.mallfoundry.analytics.stream.order;

import org.mallfoundry.dw.OrderItemFact;
import org.mallfoundry.dw.OrderQuantityFact;

import java.util.Collection;
import java.util.List;

public interface OrderItemFactRepository {

    OrderItemFact save(OrderItemFact item);

    List<OrderItemFact> findAllById(Iterable<String> ids);

    List<OrderItemFact> saveAll(Collection<OrderItemFact> items);

    List<OrderQuantityFact> countAll(List<OrderItemFact> items);
}
