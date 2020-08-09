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

import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreEdition;
import org.mallfoundry.store.StoreStatus;

import java.util.Date;

public class DelegatingImmutableFollowingStore implements ImmutableFollowingStore {

    private final Store store;

    private final StoreFollowing following;

    public DelegatingImmutableFollowingStore(Store store, StoreFollowing following) {
        this.store = store;
        this.following = following;
    }

    @Override
    public Date getFollowedTime() {
        return this.following.getFollowedTime();
    }

    @Override
    public String getId() {
        return this.store.getId();
    }

    @Override
    public String getName() {
        return this.store.getName();
    }

    @Override
    public StoreEdition getEdition() {
        return this.store.getEdition();
    }

    @Override
    public StoreStatus getStatus() {
        return this.store.getStatus();
    }

    @Override
    public String getLogo() {
        return this.store.getLogo();
    }

    @Override
    public String getOwnerId() {
        return this.store.getOwnerId();
    }

    @Override
    public String getIndustry() {
        return this.store.getIndustry();
    }

    @Override
    public String getDescription() {
        return this.store.getDescription();
    }

    @Override
    public String getCountryCode() {
        return this.store.getCountryCode();
    }

    @Override
    public String getPhone() {
        return this.store.getPhone();
    }

    @Override
    public String getZip() {
        return this.store.getZip();
    }

    @Override
    public String getProvinceId() {
        return this.store.getProvinceId();
    }

    @Override
    public String getProvince() {
        return this.store.getProvince();
    }

    @Override
    public String getCityId() {
        return this.store.getCityId();
    }

    @Override
    public String getCity() {
        return this.store.getCity();
    }

    @Override
    public String getCountyId() {
        return this.store.getCountyId();
    }

    @Override
    public String getCounty() {
        return this.store.getCounty();
    }

    @Override
    public String getAddress() {
        return this.store.getAddress();
    }

    @Override
    public Date getCreatedTime() {
        return this.store.getCreatedTime();
    }
}
