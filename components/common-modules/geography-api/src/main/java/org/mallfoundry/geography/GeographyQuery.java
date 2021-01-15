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

public interface GeographyQuery {

    String getCountryId();

    void setCountryId(String countryId);

    String getProvinceId();

    void setProvinceId(String provinceId);

    String getCityId();

    void setCityId(String cityId);

    String getCode();

    void setCode(String code);

    byte getScope();

    void setScope(byte scope);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final GeographyQuery query;

        public Builder(GeographyQuery query) {
            this.query = query;
        }

        public Builder countryId(String countryId) {
            this.query.setCountryId(countryId);
            return this;
        }

        public Builder provinceId(String provinceId) {
            this.query.setProvinceId(provinceId);
            return this;
        }

        public Builder cityId(String cityId) {
            this.query.setCityId(cityId);
            return this;
        }

        public Builder code(String code) {
            this.query.setCode(code);
            return this;
        }

        public Builder scope(byte scope) {
            this.query.setScope(scope);
            return this;
        }

        public GeographyQuery build() {
            return this.query;
        }
    }

}
