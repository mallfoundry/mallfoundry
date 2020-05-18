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
import com.mallfoundry.customer.CustomerService;
import com.mallfoundry.customer.ShippingAddress;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
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
    public void saveCustomer(@PathVariable("customer_id") String customerId, @RequestBody CustomerRequest request) {
        var customer = this.customerService.getCustomer(customerId).orElseThrow();

        if (Objects.nonNull(request.getGender())) {
            customer.setGender(request.getGender());
        }

        if (Objects.nonNull(request.getBirthday())) {
            customer.setBirthday(request.getBirthday());
        }

        this.customerService.saveCustomer(customer);
    }

    @PostMapping("/customers/{customer_id}/shipping-addresses")
    public void addShippingAddress(@PathVariable("customer_id") String id, @RequestBody ShippingAddress address) {
        this.customerService.addShippingAddress(id, address);
    }

    @PatchMapping("/customers/{customer_id}/shipping-addresses/{address_id}")
    public void saveShippingAddress(@PathVariable("customer_id") String customerId,
                                    @PathVariable("address_id") String addressId,
                                    @RequestBody ShippingAddressRequest request) {

        var address = this.customerService.getShippingAddress(customerId, addressId).orElseThrow();

        if (StringUtils.isNotBlank(request.getFirstName())) {
            address.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNotBlank(request.getLastName())) {
            address.setLastName(request.getLastName());
        }

        if (StringUtils.isNotBlank(request.getCountryCode())) {
            address.setCountryCode(request.getCountryCode());
        }

        if (StringUtils.isNotBlank(request.getAddress())) {
            address.setAddress(request.getAddress());
        }

        if (StringUtils.isNotBlank(request.getLocation())) {
            address.setLocation(request.getLocation());
        }

        if (StringUtils.isNotBlank(request.getMobile())) {
            address.setMobile(request.getMobile());
        }

        if (StringUtils.isNotBlank(request.getZipCode())) {
            address.setZipCode(request.getZipCode());
        }

        if (address.isDefaulted() != request.isDefaulted()) {
            address.setDefaulted(request.isDefaulted());
        }

        this.customerService.saveShippingAddress(customerId, address);
    }

    @DeleteMapping("/customers/{customer_id}/shipping-addresses/{address_id}")
    public void removeShippingAddress(@PathVariable("customer_id") String id,
                                      @PathVariable("address_id") String addressId) {
        this.customerService.removeShippingAddress(id, addressId);
    }

    @GetMapping("/customers/{customer_id}/shipping-addresses")
    public List<ShippingAddress> getShippingAddresses(@PathVariable("customer_id") String id) {
        return this.customerService.getShippingAddresses(id);
    }

    @GetMapping("/customers/{customer_id}/shipping-addresses/{address_id}")
    public Optional<ShippingAddress> getShippingAddress(@PathVariable("customer_id") String customerId,
                                                        @PathVariable("address_id") String addressId) {
        return this.customerService.getShippingAddress(customerId, addressId);
    }

    @GetMapping("/customers/{customer_id}/shipping-addresses/default")
    public Optional<ShippingAddress> getDefaultShippingAddress(@PathVariable("customer_id") String id) {
        return this.customerService.getDefaultShippingAddress(id);
    }
//
//    @PostMapping("/customers/{customer_id}/search-terms")
//    public void addSearchTerm(@PathVariable("customer_id") String customerId,
//                              @RequestBody SearchTermRequest request) {
//        this.customerService.addSearchTerm(customerId, request.getText());
//    }
//
//    @DeleteMapping("/customers/{customer_id}/search-terms")
//    public void clearSearchTerms(@PathVariable("customer_id") String customerId) {
//        this.customerService.clearSearchTerms(customerId);
//    }
//
//    @GetMapping("/customers/{customer_id}/search-terms")
//    public List<SearchTerm> getSearchTerms(@PathVariable("customer_id") String customerId) {
//        return this.customerService.getSearchTerms(customerId);
//    }

}
