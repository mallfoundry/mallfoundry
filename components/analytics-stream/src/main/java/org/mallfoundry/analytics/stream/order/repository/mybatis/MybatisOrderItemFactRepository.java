package org.mallfoundry.analytics.stream.order.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.models.OrderItemFact;
import org.mallfoundry.analytics.models.OrderQuantityFact;
import org.mallfoundry.analytics.stream.order.OrderItemFactRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface MybatisOrderItemFactRepository extends OrderItemFactRepository {

    void insert(@Param("item") OrderItemFact item);

    int update(@Param("item") OrderItemFact item);

    List<OrderItemFact> findAllById(@Param("ids") Iterable<String> ids);

    @Override
    default List<OrderItemFact> saveAll(Collection<OrderItemFact> items) {
        List<String> ids = new ArrayList<>();
        for (var item : items) {
            if (this.update(item) == 0) {
                this.insert(item);
            }
            ids.add(item.getId());
        }
        return this.findAllById(ids);
    }

    @Override
    List<OrderQuantityFact> countAll(@Param("items") List<OrderItemFact> items);
}
