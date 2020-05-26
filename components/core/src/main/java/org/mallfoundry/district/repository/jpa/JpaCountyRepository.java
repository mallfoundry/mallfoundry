package org.mallfoundry.district.repository.jpa;

import org.mallfoundry.district.CountyRepository;
import org.mallfoundry.district.InternalCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCountyRepository
        extends CountyRepository, JpaRepository<InternalCounty, String> {
}
