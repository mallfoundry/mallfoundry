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

import org.mallfoundry.identity.User;
import org.mallfoundry.store.StoreEdition;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.StoreRating;
import org.mallfoundry.store.StoreStatus;

import java.util.Date;
import java.util.List;

public interface ImmutableFollowingStore extends FollowingStore {

    @Override
    default StoreId toId() {
        return null;
    }

    @Override
    default void setId(String id) {

    }

    @Override
    default String getAccountId() {
        return null;
    }

    @Override
    default void bindAccount(String accountId) {

    }

    @Override
    default String getName() {
        return null;
    }

    @Override
    default void setName(String name) {

    }

    @Override
    default StoreEdition getEdition() {
        return null;
    }

    @Override
    default StoreStatus getStatus() {
        return null;
    }

    @Override
    default String getLogo() {
        return null;
    }

    @Override
    default void setLogo(String logo) {

    }

    @Override
    default String getIndustry() {
        return null;
    }

    @Override
    default void setIndustry(String industry) {

    }

    @Override
    default String getDescription() {
        return null;
    }

    @Override
    default void setDescription(String description) {

    }

    @Override
    default String getCountryCode() {
        return null;
    }

    @Override
    default void setCountryCode(String countryCode) {

    }

    @Override
    default String getPhone() {
        return null;
    }

    @Override
    default void setPhone(String phone) {

    }

    @Override
    default List<StoreRating> getRatings() {
        return null;
    }

    @Override
    default StoreRating rating(StoreRating rating) {
        return null;
    }

    @Override
    default String getZip() {
        return null;
    }

    @Override
    default void setZip(String zip) {

    }

    @Override
    default String getProvinceId() {
        return null;
    }

    @Override
    default void setProvinceId(String provinceId) {

    }

    @Override
    default String getProvince() {
        return null;
    }

    @Override
    default void setProvince(String province) {

    }

    @Override
    default String getCityId() {
        return null;
    }

    @Override
    default void setCityId(String cityId) {

    }

    @Override
    default String getCity() {
        return null;
    }

    @Override
    default void setCity(String city) {

    }

    @Override
    default String getCountyId() {
        return null;
    }

    @Override
    default void setCountyId(String countyId) {

    }

    @Override
    default String getCounty() {
        return null;
    }

    @Override
    default void setCounty(String county) {

    }

    @Override
    default String getAddress() {
        return null;
    }

    @Override
    default void setAddress(String address) {

    }

    @Override
    default Date getCreatedTime() {
        return null;
    }

    @Override
    default void create() {

    }

    @Override
    default void initialize() {

    }

    @Override
    default void open() {

    }

    @Override
    default void resume() {

    }

    @Override
    default void pause() {

    }

    @Override
    default void close() {

    }

    @Override
    default void changeOwner(User ownerId) {

    }

    @Override
    default String getOwnerId() {
        return null;
    }

    @Override
    default String getTenantId() {
        return null;
    }

    @Override
    default Builder toBuilder() {
        return null;
    }
}
