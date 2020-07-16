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

package org.mallfoundry.district;

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InternalDistrictService implements DistrictService {

    private static final String DISTRICT_ID_VALUE_NAME = "district.id";

    private static final String REGION_ID_VALUE_NAME = DISTRICT_ID_VALUE_NAME;

    private static final String PROVINCE_ID_VALUE_NAME = DISTRICT_ID_VALUE_NAME;

    private static final String CITY_ID_VALUE_NAME = DISTRICT_ID_VALUE_NAME;

    private static final String COUNTY_ID_VALUE_NAME = DISTRICT_ID_VALUE_NAME;

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
        return new InternalRegion(code, name, countryId);
    }

    @Override
    public Province createProvince(String code, String name, String countryId) {
        return new InternalProvince(code, name, countryId);
    }

    @Override
    public City createCity(String code, String name, String provinceId) {
        return new InternalCity(code, name, provinceId);
    }

    @Override
    public County createCounty(String code, String name, String cityId) {
        return new InternalCounty(code, name, cityId);
    }

    @Override
    public Region addRegion(Region aRegion) {
        var region = InternalRegion.of(aRegion);
        region.setId(PrimaryKeyHolder.next(REGION_ID_VALUE_NAME));
        return this.regionRepository.save(region);
    }

    @Transactional
    @Override
    public Province addProvince(Province aProvince) {
        var province = InternalProvince.of(aProvince);
        province.setId(PrimaryKeyHolder.next(PROVINCE_ID_VALUE_NAME));
        return this.provinceRepository.save(province);
    }

    @Override
    public City addCity(City aCity) {
        var city = InternalCity.of(aCity);
        city.setId(PrimaryKeyHolder.next(CITY_ID_VALUE_NAME));
        return this.cityRepository.save(city);
    }

    @Override
    public County addCounty(County aCounty) {
        var county = InternalCounty.of(aCounty);
        county.setId(PrimaryKeyHolder.next(COUNTY_ID_VALUE_NAME));
        return this.countyRepository.save(county);
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
    public List<City> getCities(DistrictQuery query) {
        return CastUtils.cast(this.cityRepository.findAllByProvinceId(query.getProvinceId()));
    }

    @Override
    public List<County> getCounties(DistrictQuery query) {
        return CastUtils.cast(this.countyRepository.findAll(query));
    }

    @Override
    public Optional<County> getCounty(String id) {
        return CastUtils.cast(this.countyRepository.findById(id));
    }
}
