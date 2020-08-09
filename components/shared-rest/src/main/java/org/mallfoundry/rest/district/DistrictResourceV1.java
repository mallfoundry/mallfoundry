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

package org.mallfoundry.rest.district;

import org.mallfoundry.district.DistrictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/district")
public class DistrictResourceV1 {

    private final DistrictService districtService;

    private final DistrictRestService districtRestService;

    public DistrictResourceV1(DistrictService districtService,
                              DistrictRestService districtRestService) {
        this.districtService = districtService;
        this.districtRestService = districtRestService;
    }

    @GetMapping("/countries")
    public List<CountryResponse> getCountries(@RequestParam(required = false, defaultValue = "0") byte scope) {
        return this.districtRestService.getCountries(
                this.districtService.createQuery().toBuilder().scope(scope).build());
    }

    @GetMapping("/countries/{country_id}/regions")
    public List<RegionResponse> getRegions(@PathVariable("country_id") String countryId,
                                           @RequestParam(required = false, defaultValue = "0") byte scope) {
        return this.districtRestService.getRegions(
                this.districtService.createQuery().toBuilder().countryId(countryId).scope(scope).build());
    }

    @GetMapping("/countries/{country_id}/provinces")
    public List<ProvinceResponse> getProvinces(@PathVariable("country_id") String countryId,
                                               @RequestParam(required = false, defaultValue = "0") byte scope) {
        return this.districtRestService.getProvinces(
                this.districtService.createQuery().toBuilder().countryId(countryId).scope(scope).build());
    }

    @GetMapping("/provinces/{province_id}/cities")
    public List<CityResponse> getCities(@PathVariable("province_id") String provinceId,
                                        @RequestParam(required = false, defaultValue = "0") byte scope) {
        return this.districtRestService.getCities(
                this.districtService.createQuery().toBuilder().provinceId(provinceId).scope(scope).build());
    }

    @GetMapping("/cities/{city_id}/counties")
    public List<CountyResponse> getCounties(@PathVariable("city_id") String cityId,
                                            @RequestParam(required = false) String code) {
        return this.districtRestService.getCounties(
                this.districtService.createQuery().toBuilder().cityId(cityId).code(code).build());
    }

    @GetMapping("/counties/{county_id}")
    public Optional<CountyResponse> getCounty(@PathVariable("county_id") String countyId) {
        return this.districtService.getCounty(countyId).map(CountyResponse::new);
    }
}
