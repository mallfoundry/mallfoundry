package org.mallfoundry.analytics.stream.order.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.stream.models.OrderItemFact;
import org.mallfoundry.analytics.stream.models.OrderQuantityFact;
import org.mallfoundry.analytics.stream.order.OrderItemFactRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Repository
public interface MybatisOrderItemFactRepository extends OrderItemFactRepository {

    void insertAll(@Param("items") Collection<OrderItemFact> items);

    List<OrderItemFact> findAllById(@Param("ids") Iterable<String> ids);

    @Override
    default List<OrderItemFact> saveAll(Collection<OrderItemFact> items) {
        this.insertAll(items);
        var ids = items.stream().map(OrderItemFact::getId).collect(Collectors.toUnmodifiableList());
        return this.findAllById(ids);
    }

    @Override
    List<OrderQuantityFact> countAll(@Param("items") List<OrderItemFact> items);
}
