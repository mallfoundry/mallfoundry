package org.mallfoundry.catalog.jpa;

import org.mallfoundry.catalog.BrandRepository;
import org.mallfoundry.catalog.InternalBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBrandRepository
        extends BrandRepository, JpaRepository<InternalBrand, String> {
}
