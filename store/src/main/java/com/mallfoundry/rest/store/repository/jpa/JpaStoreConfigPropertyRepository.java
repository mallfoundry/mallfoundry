package com.mallfoundry.rest.store.repository.jpa;

import com.mallfoundry.rest.store.StoreConfigProperty;
import com.mallfoundry.rest.store.StoreConfigPropertyId;
import com.mallfoundry.rest.store.StoreConfigPropertyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStoreConfigPropertyRepository
        extends StoreConfigPropertyRepository,
        JpaRepository<StoreConfigProperty, StoreConfigPropertyId> {

}
