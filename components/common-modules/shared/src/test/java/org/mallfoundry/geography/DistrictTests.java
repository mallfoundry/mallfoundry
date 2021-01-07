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

package org.mallfoundry.geography;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StandaloneTest;
import org.mallfoundry.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@StandaloneTest
public class DistrictTests {

    @Autowired
    private GeographyService districtService;

    @Rollback(false)
    @Transactional
    @Test
    public void testSaveRegions() throws IOException {
        var jsonString = FileUtils.readFileToString(new File("region.json"), "utf-8");
        List<JsonRegion> regions = JsonUtils.parse(jsonString, List.class, JsonRegion.class);
        for (var region : regions) {
            var savedRegion = saveRegion(this.getRegionCode(region.getName()), region.getName());

            for (var province : region.getProvinces()) {
                var savedProvince = this.saveProvince(province.getCode(), province.getName());
                savedRegion.getProvinces().add(savedProvince);
                for (var city : province.getCities()) {
                    var savedCity = this.saveCity(city.getCode(), city.getName(), savedProvince.getId());
                    for (var county : city.getCounties()) {
                        this.saveCounty(county.getCode(), county.getName(), savedCity.getId());
                    }
                }
            }
        }
    }

    private String getRegionCode(String name) {
        if ("东北".equals(name)) {
            return "NE";
        } else if ("华东".equals(name)) {
            return "EC";
        } else if ("华中".equals(name)) {
            return "CC";
        } else if ("华南".equals(name)) {
            return "SC";
        } else if ("华北".equals(name)) {
            return "NC";
        } else if ("西北".equals(name)) {
            return "NW";
        } else if ("西南".equals(name)) {
            return "SW";
        } else if ("港澳台".equals(name)) {
            return "HMT";
        }

        return null;
    }

    private Region saveRegion(String code, String name) {
        return this.districtService.addRegion(
                this.districtService.createRegion(null)
                        .toBuilder().countryId("1").code(code).name(name).build());
    }

    private Province saveProvince(String code, String name) {
        return this.districtService.addProvince(
                this.districtService.createProvince(null)
                        .toBuilder().countryId("1").code(code).name(name).build());
    }

    private void saveCounty(String code, String name, String cityId) {
        this.districtService.addCounty(
                this.districtService.createCounty(null)
                        .toBuilder().cityId(cityId).code(code).name(name).build());
    }

    private City saveCity(String code, String name, String provinceId) {
        return this.districtService.addCity(
                this.districtService.createCity(null)
                        .toBuilder().provinceId(provinceId).code(code).name(name).build());
    }

}
