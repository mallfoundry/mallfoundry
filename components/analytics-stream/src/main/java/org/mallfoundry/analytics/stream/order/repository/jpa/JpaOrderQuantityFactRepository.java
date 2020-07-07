package org.mallfoundry.analytics.stream.order.repository.jpa;

import org.mallfoundry.analytics.stream.models.OrderQuantityFact;
import org.mallfoundry.analytics.stream.order.OrderQuantityFactRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaOrderQuantityFactRepository implements OrderQuantityFactRepository {

    private final JpaOrderQuantityFactRepositoryDelegate repository;

    public JpaOrderQuantityFactRepository(JpaOrderQuantityFactRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderQuantityFact> saveAll(Collection<OrderQuantityFact> facts) {
        return CastUtils.cast(
                this.repository.saveAll(
                        facts.stream()
                                .map(JpaOrderQuantityFact::of)
                                .collect(Collectors.toList())));
    }
}
