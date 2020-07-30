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

import org.mallfoundry.data.SliceList;

import java.util.Optional;

public class DefaultStoreAddressService implements StoreAddressService {

    private final StoreAddressRepository addressRepository;

    public DefaultStoreAddressService(StoreAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public StoreAddressQuery createAddressQuery() {
        return new DefaultStoreAddressQuery();
    }

    @Override
    public StoreAddress createAddress(String addressId) {
        return this.addressRepository.create(addressId);
    }

    @Override
    public StoreAddress addAddress(StoreAddress address) {
        return this.addressRepository.save(address);
    }

    @Override
    public StoreAddress updateAddress(StoreAddress address) {
        return this.addressRepository.save(address);
    }

    @Override
    public void deleteAddress(String addressId) {
        var address = this.requiredAddress(addressId);
        this.addressRepository.delete(address);
    }

    private StoreAddress requiredAddress(String addressId) {
        return this.getAddress(addressId).orElseThrow();
    }

    @Override
    public Optional<StoreAddress> getAddress(String addressId) {
        return this.addressRepository.findById(addressId);
    }

    @Override
    public SliceList<StoreAddress> getAddresses(StoreAddressQuery query) {
        return this.addressRepository.findAll(query);
    }
}
