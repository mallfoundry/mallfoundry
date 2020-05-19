/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.customer;

import com.mallfoundry.identity.UserService;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InternalCustomerService implements CustomerService {

    private final UserService userService;

    private final CustomerRepository customerRepository;

    public InternalCustomerService(UserService userService, CustomerRepository customerRepository) {
        this.userService = userService;
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(String userId) {
        var user = this.userService.getUser(this.userService.createUserId(userId)).orElseThrow();
        var customer = new InternalCustomer(userId);
        customer.setNickname(user.getNickname());
        return customer;
    }

    @Override
    public ShippingAddress createShippingAddress() {
        return new InternalShippingAddress();
    }

    @Override
    public Optional<Customer> getCustomer(String customerId) {
        return CastUtils.cast(this.customerRepository.findById(customerId));
    }

    @Transactional
    @Override
    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(InternalCustomer.of(customer));
    }

    @Transactional
    @Override
    public void deleteCustomer(String customerId) {
        this.customerRepository.deleteById(customerId);
    }

    @Transactional
    @Override
    public void addShippingAddress(String customerId, ShippingAddress address) {
        this.getCustomer(customerId).orElseThrow().addShippingAddress(address);
    }

    @Transactional
    @Override
    public List<ShippingAddress> getShippingAddresses(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getShippingAddresses();
    }

    @Override
    public Optional<ShippingAddress> getShippingAddress(String customerId, String addressId) {
        return this.getCustomer(customerId).orElseThrow().getShippingAddress(addressId);
    }

    @Override
    public Optional<ShippingAddress> getDefaultShippingAddress(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getDefaultShippingAddress();
    }

    @Transactional
    @Override
    public void setShippingAddress(String customerId, ShippingAddress newAddress) {
        this.getCustomer(customerId).orElseThrow().addShippingAddress(newAddress);
    }

    @Transactional
    @Override
    public void removeShippingAddress(String customerId, String addressId) {
        var customer = this.getCustomer(customerId).orElseThrow();
        customer.removeShippingAddress(customer.getShippingAddress(addressId).orElseThrow());
    }
}
