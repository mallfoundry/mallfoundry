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
    public void addDeliveryAddress(String customerId, DeliveryAddress deliveryAddress) {
        this.getCustomer(customerId).orElseThrow().addDeliveryAddress(deliveryAddress);
    }

    @Transactional
    public void removeDeliveryAddress(String customerId, Long deliveryAddressId) {
        Customer customer = this.getCustomer(customerId).orElseThrow();
        customer.getDeliveryAddress(deliveryAddressId).ifPresent(customer::removeDeliveryAddress);
    }

    @Transactional
    public List<DeliveryAddress> getDeliveryAddresses(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getDeliveryAddresses();
    }

    @Transactional
    public Optional<DeliveryAddress> getDeliveryAddress(String customerId, Long deliveryAddressId) {
        return this.getCustomer(customerId).orElseThrow().getDeliveryAddress(deliveryAddressId);
    }

    @Transactional
    public Optional<DeliveryAddress> getDefaultDeliveryAddress(String customerId) {
        return this.getCustomer(customerId).orElseThrow().getDefaultDeliveryAddress();
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
