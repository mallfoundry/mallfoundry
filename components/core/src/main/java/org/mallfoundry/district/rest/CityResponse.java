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

package org.mallfoundry.district.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mallfoundry.district.City;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class CityResponse {

    @JsonIgnore
    private final City city;

    @Getter
    private List<CountyResponse> counties;

    public CityResponse(City city, int scope) {
        this.city = city;
        if (scope > 0) {
            this.counties = city
                    .getCounties()
                    .stream()
                    .map(CountyResponse::new)
                    .collect(Collectors.toList());
        }
    }

    public String getProvinceId() {
        return this.city.getProvinceId();
    }

    public String getId() {
        return this.city.getId();
    }

    public String getCode() {
        return this.city.getCode();
    }

    public String getName() {
        return this.city.getName();
    }

    public long getPosition() {
        return this.city.getPosition();
    }
}
