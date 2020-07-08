package org.mallfoundry.analytics.stream.order;

import org.mallfoundry.analytics.models.OrderItemFact;
import org.mallfoundry.analytics.models.OrderQuantityFact;

import java.util.Collection;
import java.util.List;

public interface OrderQuantityFactRepository {

    void deleteAll(List<OrderItemFact> facts);

    void saveAll(Collection<OrderQuantityFact> facts);
}
