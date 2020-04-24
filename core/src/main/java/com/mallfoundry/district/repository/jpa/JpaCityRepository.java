package com.mallfoundry.district.repository.jpa;

import com.mallfoundry.district.CityRepository;
import com.mallfoundry.district.InternalCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCityRepository
        extends CityRepository, JpaRepository<InternalCity, String> {
}
