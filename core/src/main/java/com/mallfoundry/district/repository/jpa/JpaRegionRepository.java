package com.mallfoundry.district.repository.jpa;

import com.mallfoundry.district.InternalRegion;
import com.mallfoundry.district.RegionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRegionRepository
        extends RegionRepository, JpaRepository<InternalRegion, String> {
}
