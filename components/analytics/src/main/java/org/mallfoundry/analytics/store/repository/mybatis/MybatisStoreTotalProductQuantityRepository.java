package org.mallfoundry.analytics.store.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.store.StoreTotalProductQuantity;
import org.mallfoundry.analytics.store.StoreTotalProductQuantityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface MybatisStoreTotalProductQuantityRepository extends StoreTotalProductQuantityRepository {

    StoreTotalProductQuantity selectByStoreId(@Param("storeId") String storeId);

    @Override
    default Optional<StoreTotalProductQuantity> findByStoreId(String storeId) {
        return Optional.ofNullable(this.selectByStoreId(storeId));
    }
}
