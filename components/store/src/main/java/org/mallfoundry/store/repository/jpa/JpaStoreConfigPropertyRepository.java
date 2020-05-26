package org.mallfoundry.store.repository.jpa;

import org.mallfoundry.store.StoreConfigProperty;
import org.mallfoundry.store.StoreConfigPropertyId;
import org.mallfoundry.store.StoreConfigPropertyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStoreConfigPropertyRepository
        extends StoreConfigPropertyRepository,
        JpaRepository<StoreConfigProperty, StoreConfigPropertyId> {

}
