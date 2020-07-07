package org.mallfoundry.analytics.store.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.mallfoundry.analytics.store.StoreTotalOrderQuantity;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StoreTotalOrderQuantityMapper {

    StoreTotalOrderQuantity selectByStoreId(String storeId);
}
