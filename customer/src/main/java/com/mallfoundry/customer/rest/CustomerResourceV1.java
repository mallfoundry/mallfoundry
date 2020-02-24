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

package com.mallfoundry.customer.rest;

import com.mallfoundry.customer.Customer;
import com.mallfoundry.customer.CustomerId;
import com.mallfoundry.customer.CustomerService;
import com.mallfoundry.customer.DeliveryAddress;
import com.mallfoundry.customer.SearchTerm;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/v1")
@RestController
public class CustomerResourceV1 {

    private final CustomerService customerService;

    public CustomerResourceV1(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{customer_id}")
    public Optional<Customer> getCustomer(@PathVariable("customer_id") String id) {
        return this.customerService.getCustomer(id);
    }

    @PatchMapping("/customers/{customer_id}")
    public Customer updateCustomer(@PathVariable("customer_id") String id,
                                   @RequestBody Customer customer) {
        customer.setId(new CustomerId(id));
        return this.customerService.saveCustomer(customer);
    }

    @PostMapping("/customers/{customer_id}/delivery_addresses")
    public void addDeliveryAddress(@PathVariable("customer_id") String id,
                                   @RequestBody DeliveryAddress deliveryAddress) {
        this.customerService.addDeliveryAddress(id, deliveryAddress);
    }

    @PutMapping("/customers/{customer_id}/delivery_addresses/{delivery_address_id}")
    public void addDeliveryAddress(@PathVariable("customer_id") String customerId,
                                   @PathVariable("delivery_address_id") Long deliveryAddressId,
                                   @RequestBody DeliveryAddress deliveryAddress) {
        deliveryAddress.setId(deliveryAddressId);
        this.customerService.addDeliveryAddress(customerId, deliveryAddress);
    }

    @DeleteMapping("/customers/{customer_id}/delivery_addresses/{delivery_address_id}")
    public void removeDeliveryAddress(@PathVariable("customer_id") String id,
                                      @PathVariable("delivery_address_id") Long deliveryAddressId) {
        this.customerService.removeDeliveryAddress(id, deliveryAddressId);
    }

    @GetMapping("/customers/{customer_id}/delivery_addresses")
    public List<DeliveryAddress> getDeliveryAddresses(@PathVariable("customer_id") String id) {
        return this.customerService.getDeliveryAddresses(id);
    }

    @GetMapping("/customers/{customer_id}/delivery_addresses/{delivery_address_id}")
    public Optional<DeliveryAddress> getDeliveryAddress(
            @PathVariable("customer_id") String customerId,
            @PathVariable("delivery_address_id") Long deliveryAddressId) {
        return this.customerService.getDeliveryAddress(customerId, deliveryAddressId);
    }

    @GetMapping("/customers/{customer_id}/delivery_addresses/default")
    public Optional<DeliveryAddress> getDefaultDeliveryAddress(@PathVariable("customer_id") String id) {
        return this.customerService.getDefaultDeliveryAddress(id);
    }

    @PostMapping("/customers/{customer_id}/search_terms")
    public void addSearchTerm(@PathVariable("customer_id") String customerId,
                              @RequestBody SearchTermRequest request) {
        this.customerService.addSearchTerm(customerId, request.getText());
    }

    @DeleteMapping("/customers/{customer_id}/search_terms")
    public void clearSearchTerms(@PathVariable("customer_id") String customerId) {
        this.customerService.clearSearchTerms(customerId);
    }

    @GetMapping("/customers/{customer_id}/search_terms")
    public List<SearchTerm> getSearchTerms(@PathVariable("customer_id") String customerId) {
        return this.customerService.getSearchTerms(customerId);
    }

}
