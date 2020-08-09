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

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.district.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CountryResponse {
    private String id;
    private String code;
    private String name;
    private List<ProvinceResponse> provinces = new ArrayList<>();
    private int position;

    public CountryResponse(Country country, int scope) {
        this.id = country.getId();
        this.code = country.getCode();
        this.name = country.getName();
        this.position = country.getPosition();
        if (scope > 0) {
            this.provinces = country
                    .getProvinces()
                    .stream()
                    .map(province -> new ProvinceResponse(province, scope - 1))
                    .collect(Collectors.toList());
        }
    }
}
