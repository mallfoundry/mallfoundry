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

package org.mallfoundry.customer;

import org.mallfoundry.identity.UserService;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public Customer createCustomer(String customerId) {
        return new InternalCustomer(customerId);
    }

    @Override
    public Optional<Customer> getCustomer(String customerId) {
        return CastUtils.cast(this.customerRepository.findById(customerId));
    }

    @Override
    public Customer addCustomer(Customer customer) {
        var user = this.userService.getUser(customer.getUserId()).orElseThrow();
        customer.setNickname(user.getNickname());
        return this.customerRepository.save(InternalCustomer.of(customer));
    }

    @Transactional
    @Override
    public void updateCustomer(Customer customer) {
        var savedCustomer = this.customerRepository.findById(customer.getId()).orElseThrow();
        if (Objects.nonNull(customer.getGender())) {
            savedCustomer.setGender(customer.getGender());
        }
        if (Objects.nonNull(customer.getBirthday())) {
            savedCustomer.setBirthday(customer.getBirthday());
        }
        this.customerRepository.save(savedCustomer);
    }

    @Transactional
    @Override
    public void deleteCustomer(String customerId) {
        this.customerRepository.deleteById(customerId);
    }

    @Transactional
    @Override
    public ShippingAddress addAddress(String customerId, ShippingAddress address) {
        this.getCustomer(customerId).orElseThrow().addAddress(address);
        return address;
    }

    @Transactional
    @Override
    public List<ShippingAddress> getAddresses(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getAddresses();
    }

    @Override
    public Optional<ShippingAddress> getAddress(String customerId, String addressId) {
        return this.getCustomer(customerId).orElseThrow().getAddress(addressId);
    }

    @Override
    public Optional<ShippingAddress> getDefaultAddress(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getDefaultAddress();
    }

    @Transactional
    @Override
    public void setAddress(String customerId, ShippingAddress newAddress) {
        this.getCustomer(customerId).orElseThrow().setAddress(newAddress);
    }

    @Transactional
    @Override
    public void removeAddress(String customerId, String addressId) {
        var customer = this.getCustomer(customerId).orElseThrow();
        customer.removeAddress(customer.getAddress(addressId).orElseThrow());
    }
}
