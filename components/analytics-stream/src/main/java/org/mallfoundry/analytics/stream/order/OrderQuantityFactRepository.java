package org.mallfoundry.analytics.stream.order;

import org.mallfoundry.dw.OrderItemFact;
import org.mallfoundry.dw.OrderQuantityFact;

import java.util.Collection;
import java.util.List;

public interface OrderQuantityFactRepository {

    void deleteAll(List<OrderItemFact> facts);

    void saveAll(Collection<OrderQuantityFact> facts);
}
