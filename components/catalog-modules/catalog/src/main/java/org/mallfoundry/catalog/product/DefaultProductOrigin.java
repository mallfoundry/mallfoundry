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

package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Getter
@Setter
public class DefaultProductOrigin implements ProductOrigin {

    private String provinceId;

    private String province;

    private String cityId;

    private String city;

    private String countyId;

    private String county;

    public static DefaultProductOrigin of(ProductOrigin shippingOrigin) {
        if (shippingOrigin instanceof DefaultProductOrigin) {
            return (DefaultProductOrigin) shippingOrigin;
        }
        var target = new DefaultProductOrigin();
        BeanUtils.copyProperties(shippingOrigin, target);
        return target;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultProductOrigin)) {
            return false;
        }
        DefaultProductOrigin that = (DefaultProductOrigin) o;
        return Objects.equals(provinceId, that.provinceId)
                && Objects.equals(province, that.province)
                && Objects.equals(cityId, that.cityId)
                && Objects.equals(city, that.city)
                && Objects.equals(countyId, that.countyId)
                && Objects.equals(county, that.county);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provinceId, province, cityId, city, countyId, county);
    }

    @Override
    public Builder toBuilder() {
        return new DefaultBuilder(this);
    }

    private static class DefaultBuilder implements Builder {

        private final DefaultProductOrigin shippingOrigin;

        private DefaultBuilder(DefaultProductOrigin shippingOrigin) {
            this.shippingOrigin = shippingOrigin;
        }

        @Override
        public Builder provinceId(String provinceId) {
            this.shippingOrigin.setProvinceId(provinceId);
            return this;
        }

        @Override
        public Builder province(String province) {
            this.shippingOrigin.setProvince(province);
            return this;
        }

        @Override
        public Builder cityId(String cityId) {
            this.shippingOrigin.setCityId(cityId);
            return this;
        }

        @Override
        public Builder city(String city) {
            this.shippingOrigin.setCity(city);
            return this;
        }

        @Override
        public Builder countyId(String countyId) {
            this.shippingOrigin.setCountyId(countyId);
            return this;
        }

        @Override
        public Builder county(String county) {
            this.shippingOrigin.setCounty(county);
            return this;
        }

        @Override
        public ProductOrigin build() {
            return this.shippingOrigin;
        }
    }

}
