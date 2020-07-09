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

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mallfoundry.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DistrictTests {

    @Autowired
    private DistrictService districtService;

    @Rollback(false)
    @Transactional
    @Test
    public void testSaveRegions() throws IOException {
        var jsonString = FileUtils.readFileToString(new File("d://region.json"), "utf-8");
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
        return this.districtService.addRegion(this.districtService.createRegion(code, name, "1"));
    }

    private Province saveProvince(String code, String name) {
        return this.districtService.addProvince(this.districtService.createProvince(code, name, "1"));
    }

    private void saveCounty(String code, String name, String cityId) {
        this.districtService.addCounty(this.districtService.createCounty(code, name, cityId));
    }

    private City saveCity(String code, String name, String provinceId) {
        return this.districtService.addCity(this.districtService.createCity(code, name, provinceId));
    }

}