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

package org.mallfoundry.following;

public interface ImmutableFollowingStore extends FollowingStore {

    @Override
    default void setId(String id) {

    }

    @Override
    default void setName(String name) {

    }

    @Override
    default void setLogo(String logo) {

    }

    @Override
    default void changeOwner(String ownerId) {

    }

    @Override
    default void setIndustry(String industry) {

    }

    @Override
    default void setDescription(String description) {

    }

    @Override
    default void setCountryCode(String countryCode) {

    }

    @Override
    default void setPhone(String phone) {

    }

    @Override
    default void setZip(String zip) {

    }

    @Override
    default void setProvinceId(String provinceId) {

    }

    @Override
    default void setProvince(String province) {

    }

    @Override
    default void setCityId(String cityId) {

    }

    @Override
    default void setCity(String city) {

    }

    @Override
    default void setCountyId(String countyId) {

    }

    @Override
    default void setCounty(String county) {

    }

    @Override
    default void setAddress(String address) {

    }

    @Override
    default void initialize() {

    }

    @Override
    default Builder toBuilder() {
        return null;
    }
}
