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

import org.mallfoundry.district.DistrictQuery;
import org.mallfoundry.district.DistrictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictRestService {

    private final DistrictService districtService;

    public DistrictRestService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @Transactional
    public List<CountryResponse> getCountries(DistrictQuery query) {
        return this.districtService.getCountries(query)
                .stream()
                .map(country -> new CountryResponse(country, query.getScope()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RegionResponse> getRegions(DistrictQuery query) {
        return this.districtService.getRegions(query)
                .stream()
                .map(region -> new RegionResponse(region, query.getScope()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProvinceResponse> getProvinces(DistrictQuery query) {
        return this.districtService.getProvinces(query)
                .stream()
                .map(province -> new ProvinceResponse(province, query.getScope()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CityResponse> getCities(DistrictQuery query) {
        return this.districtService.getCities(query)
                .stream()
                .map(province -> new CityResponse(province, query.getScope()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CountyResponse> getCounties(DistrictQuery query) {
        return this.districtService.getCounties(query)
                .stream()
                .map(CountyResponse::new)
                .collect(Collectors.toList());
    }
}
