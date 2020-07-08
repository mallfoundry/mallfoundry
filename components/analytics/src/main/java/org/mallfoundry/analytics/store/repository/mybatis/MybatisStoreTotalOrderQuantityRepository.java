package org.mallfoundry.analytics.store.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.store.StoreTotalOrderQuantity;
import org.mallfoundry.analytics.store.StoreTotalOrderQuantityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface MybatisStoreTotalOrderQuantityRepository extends StoreTotalOrderQuantityRepository {

    StoreTotalOrderQuantity selectByStoreId(@Param("storeId") String storeId);

    @Override
    default Optional<StoreTotalOrderQuantity> findByStoreId(String storeId) {
        return Optional.ofNullable(this.selectByStoreId(storeId));
    }
}
