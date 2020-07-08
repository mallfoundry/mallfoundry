package org.mallfoundry.analytics.stream.order.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.models.OrderItemFact;
import org.mallfoundry.analytics.models.OrderQuantityFact;
import org.mallfoundry.analytics.stream.order.OrderQuantityFactRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface MybatisOrderQuantityFactRepository extends OrderQuantityFactRepository {

    @Override
    void deleteAll(@Param("quantities") List<OrderItemFact> facts);

    @Override
    void saveAll(@Param("quantities") Collection<OrderQuantityFact> facts);
}
