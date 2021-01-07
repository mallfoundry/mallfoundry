/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.autoconfigure.district;

import org.mallfoundry.geography.CityRepository;
import org.mallfoundry.geography.CountryRepository;
import org.mallfoundry.geography.CountyRepository;
import org.mallfoundry.geography.DefaultGeographyService;
import org.mallfoundry.geography.ProvinceRepository;
import org.mallfoundry.geography.RegionRepository;
import org.mallfoundry.geography.repository.jpa.DelegatingJpaCityRepository;
import org.mallfoundry.geography.repository.jpa.DelegatingJpaCountryRepository;
import org.mallfoundry.geography.repository.jpa.DelegatingJpaCountyRepository;
import org.mallfoundry.geography.repository.jpa.DelegatingJpaProvinceRepository;
import org.mallfoundry.geography.repository.jpa.DelegatingJpaRegionRepository;
import org.mallfoundry.geography.repository.jpa.JpaCityRepository;
import org.mallfoundry.geography.repository.jpa.JpaCountryRepository;
import org.mallfoundry.geography.repository.jpa.JpaCountyRepository;
import org.mallfoundry.geography.repository.jpa.JpaProvinceRepository;
import org.mallfoundry.geography.repository.jpa.JpaRegionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistrictAutoConfiguration {

    @Bean
    public DelegatingJpaCountyRepository delegatingJpaCountyRepository(JpaCountyRepository repository) {
        return new DelegatingJpaCountyRepository(repository);
    }

    @Bean
    public DelegatingJpaCityRepository delegatingJpaCityRepository(JpaCityRepository repository) {
        return new DelegatingJpaCityRepository(repository);
    }

    @Bean
    public DelegatingJpaProvinceRepository delegatingJpaProvinceRepository(JpaProvinceRepository repository) {
        return new DelegatingJpaProvinceRepository(repository);
    }

    @Bean
    public DelegatingJpaRegionRepository delegatingJpaRegionRepository(JpaRegionRepository repository) {
        return new DelegatingJpaRegionRepository(repository);
    }

    @Bean
    public DelegatingJpaCountryRepository delegatingJpaCountryRepository(JpaCountryRepository repository) {
        return new DelegatingJpaCountryRepository(repository);
    }

    @Bean
    public DefaultGeographyService defaultDistrictService(CountryRepository countryRepository,
                                                          RegionRepository regionRepository,
                                                          ProvinceRepository provinceRepository,
                                                          CityRepository cityRepository,
                                                          CountyRepository countyRepository) {
        return new DefaultGeographyService(countryRepository, regionRepository, provinceRepository, cityRepository, countyRepository);
    }
}
