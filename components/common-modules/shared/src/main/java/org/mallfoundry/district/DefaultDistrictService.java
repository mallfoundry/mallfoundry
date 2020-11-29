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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class DefaultDistrictService implements DistrictService {

    private static final String DISTRICT_ID_VALUE_NAME = "district.id";

    private final CountryRepository countryRepository;

    private final RegionRepository regionRepository;

    private final ProvinceRepository provinceRepository;

    private final CityRepository cityRepository;

    private final CountyRepository countyRepository;

    public DefaultDistrictService(CountryRepository countryRepository,
                                  RegionRepository regionRepository,
                                  ProvinceRepository provinceRepository,
                                  CityRepository cityRepository,
                                  CountyRepository countyRepository) {
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
        this.countyRepository = countyRepository;
    }

    @Override
    public DistrictQuery createQuery() {
        return new DefaultDistrictQuery();
    }

    @Override
    public Country createCountry(String id) {
        return this.countryRepository.create(id);
    }

    @Override
    public Region createRegion(String id) {
        return this.regionRepository.create(id);
    }

    @Override
    public Province createProvince(String id) {
        return this.provinceRepository.create(id);
    }

    @Override
    public City createCity(String id) {
        return this.cityRepository.create(id);
    }

    @Override
    public County createCounty(String id) {
        return this.countyRepository.create(id);
    }

    @Transactional
    @Override
    public Country addCountry(Country country) {
        country.setId(PrimaryKeyHolder.next(DISTRICT_ID_VALUE_NAME));
        return this.countryRepository.save(country);
    }

    @Transactional
    @Override
    public Region addRegion(Region region) {
        region.setId(PrimaryKeyHolder.next(DISTRICT_ID_VALUE_NAME));
        return this.regionRepository.save(region);
    }

    @Transactional
    @Override
    public Province addProvince(Province province) {
        province.setId(PrimaryKeyHolder.next(DISTRICT_ID_VALUE_NAME));
        return this.provinceRepository.save(province);
    }

    @Transactional
    @Override
    public City addCity(City city) {
        city.setId(PrimaryKeyHolder.next(DISTRICT_ID_VALUE_NAME));
        return this.cityRepository.save(city);
    }

    @Transactional
    @Override
    public County addCounty(County county) {
        county.setId(PrimaryKeyHolder.next(DISTRICT_ID_VALUE_NAME));
        return this.countyRepository.save(county);
    }

    @Override
    public List<Country> getCountries(DistrictQuery query) {
        return this.countryRepository.findAll();
    }

    @Override
    public List<Region> getRegions(DistrictQuery query) {
        return this.regionRepository.findAllByCountryId(query.getCountryId());
    }

    @Override
    public List<Province> getProvinces(DistrictQuery query) {
        return this.provinceRepository.findAllByCountryId(query.getCountryId());
    }

    @Override
    public List<City> getCities(DistrictQuery query) {
        return this.cityRepository.findAllByProvinceId(query.getProvinceId());
    }

    @Override
    public List<County> getCounties(DistrictQuery query) {
        return this.countyRepository.findAll(query);
    }

    @Override
    public Optional<County> getCounty(String id) {
        return this.countyRepository.findById(id);
    }
}
