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
import com.mallfoundry.customer.ShippingAddress;
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

    @PostMapping("/customers/{customer_id}/shipping-addresses")
    public void addShippingAddress(@PathVariable("customer_id") String id,
                                   @RequestBody ShippingAddress address) {
        this.customerService.addShippingAddress(id, address);
    }

    @PutMapping("/customers/{customer_id}/shipping-addresses/{address_id}")
    public void addShippingAddress(@PathVariable("customer_id") String customerId,
                                   @PathVariable("address_id") Long addressId,
                                   @RequestBody ShippingAddress address) {
        address.setId(addressId);
        this.customerService.addShippingAddress(customerId, address);
    }

    @DeleteMapping("/customers/{customer_id}/shipping-addresses/{address_id}")
    public void removeShippingAddress(@PathVariable("customer_id") String id,
                                      @PathVariable("address_id") Long addressId) {
        this.customerService.removeShippingAddress(id, addressId);
    }

    @GetMapping("/customers/{customer_id}/shipping-addresses")
    public List<ShippingAddress> getShippingAddresses(@PathVariable("customer_id") String id) {
        return this.customerService.getShippingAddresses(id);
    }

    @GetMapping("/customers/{customer_id}/shipping-addresses/{address_id}")
    public Optional<ShippingAddress> getShippingAddress(
            @PathVariable("customer_id") String customerId,
            @PathVariable("address_id") Long addressId) {
        return this.customerService.getShippingAddress(customerId, addressId);
    }

    @GetMapping("/customers/{customer_id}/shipping-addresses/default")
    public Optional<ShippingAddress> getDefaultShippingAddress(@PathVariable("customer_id") String id) {
        return this.customerService.getDefaultShippingAddress(id);
    }

    @PostMapping("/customers/{customer_id}/search-terms")
    public void addSearchTerm(@PathVariable("customer_id") String customerId,
                              @RequestBody SearchTermRequest request) {
        this.customerService.addSearchTerm(customerId, request.getText());
    }

    @DeleteMapping("/customers/{customer_id}/search-terms")
    public void clearSearchTerms(@PathVariable("customer_id") String customerId) {
        this.customerService.clearSearchTerms(customerId);
    }

    @GetMapping("/customers/{customer_id}/search-terms")
    public List<SearchTerm> getSearchTerms(@PathVariable("customer_id") String customerId) {
        return this.customerService.getSearchTerms(customerId);
    }

}
