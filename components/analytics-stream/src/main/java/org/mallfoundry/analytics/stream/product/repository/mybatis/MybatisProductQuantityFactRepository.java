package org.mallfoundry.analytics.stream.product.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.models.ProductQuantityFact;
import org.mallfoundry.analytics.stream.product.ProductQuantityFactRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface MybatisProductQuantityFactRepository extends ProductQuantityFactRepository {

    @Override
    void deleteByStoreId(@Param("storeId") String storeId);

    @Override
    void saveAll(@Param("products") Collection<ProductQuantityFact> facts);
}
