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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @GetMapping("/customers/{customer_id}/delivery_addresses")
    public List<DeliveryAddress> getCustomerDeliveryAddresses(@PathVariable("customer_id") String id) {
        Optional<Customer> customerOptional = this.customerService.getCustomer(id);
        return customerOptional.isPresent() ? customerOptional.get().getDeliveryAddresses() : Collections.emptyList();
    }

    @PatchMapping("/customers/{customer_id}")
    public Customer updateCustomer(@PathVariable("customer_id") String id,
                                   @RequestBody Customer customer) {
        customer.setId(new CustomerId(id));
        return this.customerService.saveCustomer(customer);
    }

}
