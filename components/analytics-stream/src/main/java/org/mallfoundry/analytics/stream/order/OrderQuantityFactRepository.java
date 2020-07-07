package org.mallfoundry.analytics.stream.order;

import org.mallfoundry.analytics.stream.models.OrderQuantityFact;

import java.util.Collection;
import java.util.List;

public interface OrderQuantityFactRepository {
    List<OrderQuantityFact> saveAll(Collection<OrderQuantityFact> facts);
}
