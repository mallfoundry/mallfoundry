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
import org.springframework.util.ResourceUtils;

import java.util.List;

@StandaloneTest
public class CountriesTests {

    @Autowired
    private GeographyService districtService;

    @Transactional
    @Rollback(false)
    @Test
    public void testGetCountries() throws Exception {
        var file = ResourceUtils.getFile("classpath:countries.json");
        var jsonString = FileUtils.readFileToString(file, "utf-8");
        List<JsonCountry> countries = JsonUtils.parse(jsonString, List.class, JsonCountry.class);
        var i = 1;
        for (var country : countries) {
            var aCountry = this.districtService.createCountry(String.valueOf(i))
                    .toBuilder().name(country.getName()).code(country.getCode()).position(i).build();
            this.districtService.addCountry(aCountry);
            i++;
        }
    }
}
