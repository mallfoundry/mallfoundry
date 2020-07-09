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
import org.mallfoundry.district.County;

public class CountyResponse {

    @JsonIgnore
    private final County county;

    public CountyResponse(County county) {
        this.county = county;
    }

    public String getCityId() {
        return this.county.getCityId();
    }

    public String getId() {
        return this.county.getId();
    }

    public String getCode() {
        return this.county.getCode();
    }

    public String getName() {
        return this.county.getName();
    }

    public long getPosition() {
        return this.county.getPosition();
    }
}
