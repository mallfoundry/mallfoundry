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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> getCustomer(String id) {
        return this.customerRepository.findById(new CustomerId(id));
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Transactional
    public void addShippingAddress(String customerId, ShippingAddress deliveryAddress) {
        this.getCustomer(customerId).orElseThrow().addShippingAddress(deliveryAddress);
    }

    @Transactional
    public void removeShippingAddress(String customerId, Long addressId) {
        Customer customer = this.getCustomer(customerId).orElseThrow();
        customer.getShippingAddress(addressId).ifPresent(customer::removeShippingAddress);
    }

    @Transactional
    public List<ShippingAddress> getShippingAddresses(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getShippingAddresses();
    }

    @Transactional
    public Optional<ShippingAddress> getShippingAddress(String customerId, Long addressId) {
        return this.getCustomer(customerId).orElseThrow().getShippingAddress(addressId);
    }

    @Transactional
    public Optional<ShippingAddress> getDefaultShippingAddress(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getDefaultShippingAddress();
    }

    @Transactional
    public void addSearchTerm(String customerId, String searchText) {
        this.getCustomer(customerId).orElseThrow().addSearchTerm(searchText);
    }

    @Transactional
    public void clearSearchTerms(String customerId) {
        this.getCustomer(customerId).orElseThrow().clearSearchTerms();
    }

    public List<SearchTerm> getSearchTerms(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getSearchTerms();
    }

}
