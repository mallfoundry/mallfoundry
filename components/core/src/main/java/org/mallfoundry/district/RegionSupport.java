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

import java.util.List;

public abstract class RegionSupport implements Region {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport extends DistrictSupport.BuilderSupport<Region, Builder> implements Builder {

        private final RegionSupport region;

        protected BuilderSupport(RegionSupport region) {
            super(region);
            this.region = region;
        }

        @Override
        public Builder countryId(String countryId) {
            this.region.setCountryId(countryId);
            return this;
        }

        @Override
        public Builder provinces(List<Province> provinces) {
            this.region.setProvinces(provinces);
            return this;
        }
    }
}
