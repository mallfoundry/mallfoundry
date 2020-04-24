package com.mallfoundry.district.repository.jpa;

import com.mallfoundry.district.CountyRepository;
import com.mallfoundry.district.InternalCounty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCountyRepository
        extends CountyRepository, JpaRepository<InternalCounty, String> {
}
