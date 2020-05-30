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

package org.mallfoundry.rest.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.customer.Customer;
import org.mallfoundry.customer.CustomerService;
import org.mallfoundry.customer.ShippingAddress;
import org.mallfoundry.security.SecurityUserHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "Customer Resource V1", description = "顾客资源")
@RequestMapping("/v1")
@RestController
public class CustomerResourceV1 {

    private final CustomerService customerService;

    public CustomerResourceV1(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "获得当前登录的顾客对象")
    @GetMapping("/customer")
    public Optional<CustomerResponse> getCustomer() {
        return this.customerService.getCustomer(SecurityUserHolder.getUserId()).map(CustomerResponse::new);
    }

    @Operation(summary = "根据顾客标识获得顾客对象")
    @GetMapping("/customers/{customer_id}")
    public Optional<Customer> getCustomer(@PathVariable("customer_id") String id) {
        return this.customerService.getCustomer(id);
    }

    @Operation(summary = "根据顾客标识更新顾客对象")
    @PatchMapping("/customers/{customer_id}")
    public void setCustomer(@PathVariable("customer_id") String customerId,
                            @RequestBody CustomerRequest request) {
        this.customerService.setCustomer(
                request.assignToCustomer(
                        this.customerService.createCustomer(customerId)));
    }

    @Operation(summary = "添加一个顾客的收货地址对象")
    @PostMapping("/customers/{customer_id}/addresses")
    public ShippingAddress addAddress(@PathVariable("customer_id") String customerId,
                                      @RequestBody ShippingAddressRequest request) {
        return this.customerService.addAddress(customerId,
                request.assignToAddress(
                        this.customerService.getCustomer(customerId).orElseThrow().createAddress(null)));
    }

    @Operation(summary = "更新顾客的收货地址对象")
    @PatchMapping("/customers/{customer_id}/addresses/{address_id}")
    public void setAddress(@PathVariable("customer_id") String customerId,
                           @PathVariable("address_id") String addressId,
                           @RequestBody ShippingAddressRequest request) {
        this.customerService.setAddress(customerId,
                request.assignToAddress(
                        this.customerService.getCustomer(customerId).orElseThrow().createAddress(addressId)));
    }

    @Operation(summary = "删除顾客的收货地址对象")
    @DeleteMapping("/customers/{customer_id}/addresses/{address_id}")
    public void removeAddress(@PathVariable("customer_id") String id,
                              @PathVariable("address_id") String addressId) {
        this.customerService.removeAddress(id, addressId);
    }

    @Operation(summary = "获得顾客的收货地址对象集合")
    @GetMapping("/customers/{customer_id}/addresses")
    public List<ShippingAddress> getAddresses(@PathVariable("customer_id") String id) {
        return this.customerService.getAddresses(id);
    }

    @Operation(summary = "根据标识获得顾客的收货地址对象")
    @GetMapping("/customers/{customer_id}/addresses/{address_id}")
    public Optional<ShippingAddress> getAddress(@PathVariable("customer_id") String customerId,
                                                @PathVariable("address_id") String addressId) {
        return this.customerService.getAddress(customerId, addressId);
    }

    @Operation(summary = "根据标识获得顾客的默认收货地址对象")
    @GetMapping("/customers/{customer_id}/addresses/default")
    public Optional<ShippingAddress> getDefaultAddress(@PathVariable("customer_id") String id) {
        return this.customerService.getDefaultAddress(id);
    }
}
