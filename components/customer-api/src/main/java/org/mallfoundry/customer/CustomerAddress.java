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

package org.mallfoundry.customer;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;

public interface CustomerAddress extends ObjectBuilder.ToBuilder<CustomerAddress.Builder> {

    String getId();

    void setId(String id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getTag();

    void setTag(String tag);

    String getPhone();

    void setPhone(String phone);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getProvinceId();

    void setProvinceId(String provinceId);

    String getProvince();

    void setProvince(String province);

    String getCityId();

    void setCityId(String cityId);

    String getCity();

    void setCity(String city);

    String getCountyId();

    void setCountyId(String countyId);

    String getCounty();

    void setCounty(String county);

    String getAddress();

    void setAddress(String address);

    String getZip();

    void setZip(String zip);

    boolean isDefaulted();

    void setDefaulted(boolean defaulted);

    Date getCreatedTime();

    interface Builder extends ObjectBuilder<CustomerAddress> {

        Builder firstName(String firstName);

        Builder lastName(String lastName);

        Builder countryCode(String countryCode);

        Builder phone(String phone);

        Builder zip(String zip);

        Builder provinceId(String provinceId);

        Builder province(String province);

        Builder cityId(String cityId);

        Builder city(String city);

        Builder countyId(String countyId);

        Builder county(String county);

        Builder address(String address);

        Builder defaulted(boolean defaulted);

        default Builder defaulted() {
            return this.defaulted(true);
        }
    }
}
