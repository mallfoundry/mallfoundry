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
import org.mallfoundry.district.Region;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class RegionResponse {

    @JsonIgnore
    private final Region region;

    @Getter
    private List<ProvinceResponse> provinces;

    public RegionResponse(Region region, int scope) {
        this.region = region;
        if (scope > 0) {
            this.provinces = region
                    .getProvinces()
                    .stream()
                    .map(province -> new ProvinceResponse(province, scope))
                    .collect(Collectors.toList());
        }
    }

    public String getCountryId() {
        return this.region.getCountryId();
    }

    public String getId() {
        return this.region.getId();
    }

    public String getCode() {
        return this.region.getCode();
    }

    public String getName() {
        return this.region.getName();
    }

    public long getPosition() {
        return this.region.getPosition();
    }
}
