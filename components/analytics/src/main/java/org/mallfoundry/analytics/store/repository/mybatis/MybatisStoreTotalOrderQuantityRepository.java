package org.mallfoundry.analytics.store.repository.mybatis;

import org.mallfoundry.analytics.store.StoreTotalOrderQuantity;
import org.mallfoundry.analytics.store.StoreTotalOrderQuantityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MybatisStoreTotalOrderQuantityRepository implements StoreTotalOrderQuantityRepository {

    private final StoreTotalOrderQuantityMapper mapper;

    public MybatisStoreTotalOrderQuantityRepository(StoreTotalOrderQuantityMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<StoreTotalOrderQuantity> findByStoreId(String storeId) {
        return Optional.ofNullable(this.mapper.selectByStoreId(storeId));
    }
}
