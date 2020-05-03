package com.mallfoundry.district.repository.jpa;

import com.mallfoundry.district.DistrictQuery;
import com.mallfoundry.district.InternalRegion;
import com.mallfoundry.district.RegionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRegionRepository
        extends RegionRepository, JpaRepository<InternalRegion, String> {
}
