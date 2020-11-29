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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRegion {

    @JsonProperty("part")
    private String name;

    private List<Province> provinces;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Province {
        private String name;
        @JsonProperty("regionId")
        private String code;
        private List<City> cities;

    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class City {
        private String name;
        @JsonProperty("regionId")
        private String code;
        private List<County> counties;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class County {
        private String name;
        @JsonProperty("regionId")
        private String code;
    }
}
