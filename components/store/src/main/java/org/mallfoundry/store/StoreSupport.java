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

package org.mallfoundry.store;

import org.mallfoundry.identity.User;

import java.util.Date;
import java.util.Objects;

public abstract class StoreSupport implements MutableStore {

    @Override
    public StoreId toId() {
        return new ImmutableStoreId(this.getTenantId(), this.getId());
    }

    @Override
    public void bindAccountId(String accountId) {
        this.setAccountId(accountId);
    }

    @Override
    public void changeOwner(User user) {
        this.setTenantId(user.getTenantId());
        this.setOwnerId(user.getId());
    }

    @Override
    public StoreRating rating(StoreRating sRating) {
        var ratingType = sRating.getType();
        var rating = this.getRatings().stream()
                .filter(aRating -> ratingType.equals(aRating.getType()))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(rating)) {
            this.getRatings().add(sRating);
            return sRating;
        }
        rating.rating(sRating.getRating());
        return rating;
    }

    @Override
    public void create() {
        this.setStatus(StoreStatus.PENDING);
        this.setCreatedTime(new Date());
    }

    @Override
    public void initialize() {
        this.setStatus(StoreStatus.INITIALIZING);
    }

    @Override
    public void open() {
        this.setStatus(StoreStatus.OPENED);
    }

    @Override
    public void pause() {
        this.setStatus(StoreStatus.PAUSED);
    }

    @Override
    public void resume() {
        this.setStatus(StoreStatus.OPENED);
    }

    @Override
    public void close() {
        this.setStatus(StoreStatus.CLOSING);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    public abstract static class BuilderSupport implements Builder {

        private final StoreSupport store;

        public BuilderSupport(StoreSupport store) {
            this.store = store;
        }

        @Override
        public Builder id(String id) {
            this.store.setId(id);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.store.setName(name);
            return this;
        }

        @Override
        public Builder logo(String logo) {
            this.store.setLogo(logo);
            return this;
        }

        @Override
        public Builder industry(String industry) {
            this.store.setIndustry(industry);
            return this;
        }

        @Override
        public Builder description(String description) {
            this.store.setDescription(description);
            return this;
        }

        @Override
        public Builder countryCode(String countryCode) {
            this.store.setCountryCode(countryCode);
            return this;
        }

        @Override
        public Builder phone(String phone) {
            this.store.setPhone(phone);
            return this;
        }

        @Override
        public Builder zip(String zip) {
            this.store.setZip(zip);
            return this;
        }

        @Override
        public Builder provinceId(String provinceId) {
            this.store.setProvinceId(provinceId);
            return this;
        }

        @Override
        public Builder province(String province) {
            this.store.setProvince(province);
            return this;
        }

        @Override
        public Builder cityId(String cityId) {
            this.store.setCityId(cityId);
            return this;
        }

        @Override
        public Builder city(String city) {
            this.store.setCity(city);
            return this;
        }

        @Override
        public Builder countyId(String countyId) {
            this.store.setCountyId(countyId);
            return this;
        }

        @Override
        public Builder county(String county) {
            this.store.setCounty(county);
            return this;
        }

        @Override
        public Builder address(String address) {
            this.store.setAddress(address);
            return this;
        }

        @Override
        public Store build() {
            return this.store;
        }
    }
}
