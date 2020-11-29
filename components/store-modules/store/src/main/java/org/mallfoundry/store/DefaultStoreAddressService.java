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
import org.mallfoundry.processor.Processors;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class DefaultStoreAddressService implements StoreAddressService, StoreAddressProcessorInvoker {

    private List<StoreAddressProcessor> processors;

    private final StoreAddressRepository addressRepository;

    public DefaultStoreAddressService(StoreAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void setProcessors(List<StoreAddressProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public StoreAddressQuery createStoreAddressQuery() {
        return new DefaultStoreAddressQuery();
    }

    @Override
    public StoreAddressId createStoreAddressId(String tenantId, String storeId, String addressId) {
        return new ImmutableStoreAddressId(tenantId, storeId, addressId);
    }

    @Override
    public StoreAddressId createStoreAddressId(StoreId storeId, String addressId) {
        return this.createStoreAddressId(storeId.getTenantId(), storeId.getId(), addressId);
    }

    @Override
    public StoreAddressId createStoreAddressId(String addressId) {
        return this.createStoreAddressId(null, null, addressId);
    }

    @Override
    public StoreAddress createStoreAddress(String addressId) {
        return this.addressRepository.create(addressId);
    }

    @Transactional
    @Override
    public StoreAddress addStoreAddress(StoreAddress address) {
        return this.addressRepository.save(address);
    }

    @Override
    public Optional<StoreAddress> findStoreAddress(String addressId) {
        return this.addressRepository.findById(addressId);
    }

    @Override
    public StoreAddress getStoreAddress(String addressId) {
        return this.findStoreAddress(addressId).orElseThrow();
    }

    @Override
    public SliceList<StoreAddress> getStoreAddresses(StoreAddressQuery query) {
        return this.addressRepository.findAll(query);
    }

    @Transactional
    @Override
    public StoreAddress updateStoreAddress(StoreAddress address) {
        return this.addressRepository.save(address);
    }

    @Transactional
    @Override
    public void deleteStoreAddress(String addressId) {
        var address = this.requiredStoreAddress(addressId);
        this.addressRepository.delete(address);
    }

    @Transactional
    @Override
    public void clearStoreAddresses(StoreId storeId) {
        this.addressRepository.deleteAllByStoreId(storeId);
    }

    private StoreAddress requiredStoreAddress(String addressId) {
        return this.addressRepository.findById(addressId).orElseThrow();
    }

    @Override
    public StoreAddress invokePreProcessBeforeAddStoreAddress(StoreAddress address) {
        return Processors.stream(this.processors)
                .map(StoreAddressProcessor::preProcessBeforeAddStoreAddress)
                .apply(address);
    }

    @Override
    public StoreAddress invokePostProcessAfterGetStoreAddress(StoreAddress address) {
        return Processors.stream(this.processors)
                .map(StoreAddressProcessor::postProcessAfterGetStoreAddress)
                .apply(address);
    }

    @Override
    public StoreAddressQuery invokePreProcessBeforeGetStoreAddresses(StoreAddressQuery query) {
        return Processors.stream(this.processors)
                .map(StoreAddressProcessor::preProcessBeforeGetStoreAddresses)
                .apply(query);
    }

    @Override
    public StoreAddress invokePreProcessBeforeUpdateStoreAddress(StoreAddress address) {
        return Processors.stream(this.processors)
                .map(StoreAddressProcessor::preProcessBeforeUpdateStoreAddress)
                .apply(address);
    }

    @Override
    public StoreAddress invokePreProcessBeforeDeleteStoreAddress(StoreAddress address) {
        return Processors.stream(this.processors)
                .map(StoreAddressProcessor::preProcessBeforeDeleteStoreAddress)
                .apply(address);
    }
}
