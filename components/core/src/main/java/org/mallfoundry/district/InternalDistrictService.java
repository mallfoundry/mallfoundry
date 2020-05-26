/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.district;

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class InternalDistrictService implements DistrictService {

    private static final String REGION_ID_VALUE_NAME = "district.region.id";

    private static final String PROVINCE_ID_VALUE_NAME = "district.province.id";

    private static final String CITY_ID_VALUE_NAME = "district.city.id";

    private static final String COUNTY_ID_VALUE_NAME = "district.county.id";

    private final RegionRepository regionRepository;

    private final ProvinceRepository provinceRepository;

    private final CityRepository cityRepository;

    private final CountyRepository countyRepository;

    public InternalDistrictService(RegionRepository regionRepository,
                                   ProvinceRepository provinceRepository,
                                   CityRepository cityRepository,
                                   CountyRepository countyRepository) {
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
        this.countyRepository = countyRepository;
    }

    @Override
    public DistrictQuery createQuery() {
        return new InternalDistrictQuery();
    }

    @Override
    public Region createRegion(String code, String name, String countryId) {
        return new InternalRegion(PrimaryKeyHolder.next(REGION_ID_VALUE_NAME), code, name, countryId);
    }

    @Override
    public Province createProvince(String code, String name, String countryId) {
        return new InternalProvince(PrimaryKeyHolder.next(PROVINCE_ID_VALUE_NAME), code, name, countryId);
    }

    @Override
    public City createCity(String code, String name, String provinceId) {
        return new InternalCity(PrimaryKeyHolder.next(CITY_ID_VALUE_NAME), code, name, provinceId);
    }

    @Override
    public County createCounty(String code, String name, String cityId) {
        return new InternalCounty(PrimaryKeyHolder.next(COUNTY_ID_VALUE_NAME), code, name, cityId);
    }

    @Override
    public Region saveRegion(Region region) {
        if (Objects.isNull(region.getPosition())) {
            var count = this.regionRepository.countByCountryId(region.getCountryId());
            region.setPosition(count + 1);
        }
        return this.regionRepository.save(InternalRegion.of(region));
    }

    @Transactional
    @Override
    public Province saveProvince(Province province) {
        if (Objects.isNull(province.getPosition())) {
            var count = this.provinceRepository.countByCountryId(province.getCountryId());
            province.setPosition(count + 1);
        }
        return this.provinceRepository.save(InternalProvince.of(province));
    }

    @Override
    public City saveCity(City city) {
        if (Objects.isNull(city.getPosition())) {
            var count = this.cityRepository.countByProvinceId(city.getProvinceId());
            city.setPosition(count + 1);
        }
        return this.cityRepository.save(InternalCity.of(city));
    }

    @Override
    public County saveCounty(County county) {
        if (Objects.isNull(county.getPosition())) {
            var count = this.countyRepository.countByCityId(county.getCityId());
            county.setPosition(count + 1);
        }
        return this.countyRepository.save(InternalCounty.of(county));
    }

    @Override
    public List<Region> getRegions(DistrictQuery query) {
        return CastUtils.cast(this.regionRepository.findAllByCountryId(query.getCountryId()));
    }

    @Override
    public List<Province> getProvinces(DistrictQuery query) {
        return CastUtils.cast(this.provinceRepository.findAllByCountryId(query.getCountryId()));
    }

    @Override
    public List<City> getCities(String provinceId) {
        return CastUtils.cast(this.cityRepository.findAllByProvinceId(provinceId));
    }

    @Override
    public List<County> getCounties(String cityId) {
        return CastUtils.cast(this.countyRepository.findAllByCityId(cityId));
    }
}