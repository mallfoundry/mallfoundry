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

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InternalCustomerService implements CustomerService {

    private static final String ADDRESS_ID_VALUE_NAME = "customer.address.id";

    private final CustomerRepository customerRepository;

    public InternalCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(String customerId) {
        return new InternalCustomer(customerId);
    }

    @Override
    public Optional<Customer> getCustomer(String customerId) {
        return CastUtils.cast(this.customerRepository.findById(customerId));
    }

    @Transactional
    @Override
    public Customer addCustomer(Customer customer) {
        return this.customerRepository.save(InternalCustomer.of(customer));
    }

    @Transactional
    @Override
    public Customer setCustomer(Customer customer) {
        var savedCustomer = this.customerRepository.findById(customer.getId()).orElseThrow();
        if (Objects.nonNull(customer.getNickname())) {
            savedCustomer.setNickname(customer.getNickname());
        }
        if (Objects.nonNull(customer.getGender())) {
            savedCustomer.setGender(customer.getGender());
        }
        if (Objects.nonNull(customer.getBirthdate())) {
            savedCustomer.setBirthdate(customer.getBirthdate());
        }
        return this.customerRepository.save(savedCustomer);
    }

    @Transactional
    @Override
    public void deleteCustomer(String customerId) {
        this.customerRepository.deleteById(customerId);
    }

    @Transactional
    @Override
    public CustomerAddress addAddress(String customerId, CustomerAddress address) {
        address.setId(PrimaryKeyHolder.next(ADDRESS_ID_VALUE_NAME));
        this.getCustomer(customerId).orElseThrow().addAddress(address);
        return address;
    }

    @Transactional
    @Override
    public List<CustomerAddress> getAddresses(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getAddresses();
    }

    @Override
    public Optional<CustomerAddress> getAddress(String customerId, String addressId) {
        return this.getCustomer(customerId).orElseThrow().getAddress(addressId);
    }

    @Override
    public Optional<CustomerAddress> getDefaultAddress(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getDefaultAddress();
    }

    @Transactional
    @Override
    public void setAddress(String customerId, CustomerAddress newAddress) {
        this.getCustomer(customerId).orElseThrow().setAddress(newAddress);
    }

    @Transactional
    @Override
    public void removeAddress(String customerId, String addressId) {
        var customer = this.getCustomer(customerId).orElseThrow();
        customer.removeAddress(customer.getAddress(addressId).orElseThrow());
    }
}
