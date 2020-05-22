package com.mallfoundry.store.repository.jpa;

import com.mallfoundry.store.StoreConfigProperty;
import com.mallfoundry.store.StoreConfigPropertyId;
import com.mallfoundry.store.StoreConfigPropertyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStoreConfigPropertyRepository
        extends StoreConfigPropertyRepository,
        JpaRepository<StoreConfigProperty, StoreConfigPropertyId> {

}
