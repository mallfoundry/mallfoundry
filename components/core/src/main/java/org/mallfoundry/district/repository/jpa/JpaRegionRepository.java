package org.mallfoundry.district.repository.jpa;

import org.mallfoundry.district.InternalRegion;
import org.mallfoundry.district.RegionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRegionRepository
        extends RegionRepository, JpaRepository<InternalRegion, String> {
}
