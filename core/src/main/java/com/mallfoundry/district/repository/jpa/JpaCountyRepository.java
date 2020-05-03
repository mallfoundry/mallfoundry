package com.mallfoundry.district.repository.jpa;

import com.mallfoundry.district.CountyRepository;
import com.mallfoundry.district.InternalCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCountyRepository
        extends CountyRepository, JpaRepository<InternalCounty, String> {
}
