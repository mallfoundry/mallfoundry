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
import org.mallfoundry.store.StoreStatus;

import java.util.Date;

public class DelegatingFollowStore implements FollowStore {

    private final Store store;

    private final StoreFollowing following;

    public DelegatingFollowStore(Store store, StoreFollowing following) {
        this.store = store;
        this.following = following;
    }

    @Override
    public Date getFollowedTime() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public StoreStatus getStatus() {
        return null;
    }

    @Override
    public String getDomain() {
        return null;
    }

    @Override
    public void setDomain(String domain) {

    }

    @Override
    public String getLogo() {
        return null;
    }

    @Override
    public void setLogo(String logo) {

    }

    @Override
    public String getOwnerId() {
        return null;
    }

    @Override
    public void setOwnerId(String ownerId) {

    }

    @Override
    public String getIndustry() {
        return null;
    }

    @Override
    public void setIndustry(String industry) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public String getCountryCode() {
        return null;
    }

    @Override
    public void setCountryCode(String countryCode) {

    }

    @Override
    public String getMobile() {
        return null;
    }

    @Override
    public void setMobile(String mobile) {

    }

    @Override
    public String getZip() {
        return null;
    }

    @Override
    public void setZip(String zip) {

    }

    @Override
    public String getProvinceId() {
        return null;
    }

    @Override
    public void setProvinceId(String provinceId) {

    }

    @Override
    public String getProvince() {
        return null;
    }

    @Override
    public void setProvince(String province) {

    }

    @Override
    public String getCityId() {
        return null;
    }

    @Override
    public void setCityId(String cityId) {

    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public void setCity(String city) {

    }

    @Override
    public String getCountyId() {
        return null;
    }

    @Override
    public void setCountyId(String countyId) {

    }

    @Override
    public String getCounty() {
        return null;
    }

    @Override
    public void setCounty(String county) {

    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public void setAddress(String address) {

    }

    @Override
    public Date getCreatedTime() {
        return null;
    }

    @Override
    public void initialize() {

    }

    @Override
    public Builder toBuilder() {
        return null;
    }
}
