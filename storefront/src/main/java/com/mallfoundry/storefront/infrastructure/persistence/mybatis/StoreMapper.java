package com.mallfoundry.storefront.infrastructure.persistence.mybatis;

import com.mallfoundry.storefront.domain.StoreInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StoreMapper {

    StoreInfo selectById(@Param("id") String id);

    void insert(StoreInfo store);

    void update(StoreInfo store);
}
