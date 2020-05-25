package org.mallfoundry.district.repository.jpa;

import org.mallfoundry.district.CityRepository;
import org.mallfoundry.district.InternalCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCityRepository
        extends CityRepository, JpaRepository<InternalCity, String> {
}
