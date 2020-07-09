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

package org.mallfoundry.rest.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.customer.CustomerAddress;
import org.mallfoundry.customer.CustomerService;
import org.mallfoundry.security.SubjectHolder;
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
        return this.customerService.getCustomer(SubjectHolder.getUserId()).map(CustomerResponse::new);
    }

    @Operation(summary = "根据顾客标识获得顾客对象")
    @GetMapping("/customers/{customer_id}")
    public Optional<CustomerResponse> getCustomer(@PathVariable("customer_id") String id) {
        return this.customerService.getCustomer(id).map(CustomerResponse::new);
    }

    @Operation(summary = "根据顾客标识更新顾客对象")
    @PatchMapping("/customers/{customer_id}")
    public Optional<CustomerResponse> setCustomer(@PathVariable("customer_id") String customerId,
                                                  @RequestBody CustomerRequest request) {
        return Optional.ofNullable(
                this.customerService.setCustomer(
                        request.assignToCustomer(
                                this.customerService.createCustomer(customerId))))
                .map(CustomerResponse::new);
    }

    @Operation(summary = "添加一个顾客的收货地址对象")
    @PostMapping("/customers/{customer_id}/addresses")
    public CustomerAddress addAddress(@PathVariable("customer_id") String customerId,
                                      @RequestBody ShippingAddressRequest request) {
        return this.customerService.addAddress(customerId,
                request.assignToAddress(
                        this.customerService.createCustomer(customerId).createAddress(null)));
    }

    @Operation(summary = "更新顾客的收货地址对象")
    @PatchMapping("/customers/{customer_id}/addresses/{address_id}")
    public void setAddress(@PathVariable("customer_id") String customerId,
                           @PathVariable("address_id") String addressId,
                           @RequestBody ShippingAddressRequest request) {
        this.customerService.setAddress(customerId,
                request.assignToAddress(
                        this.customerService.createCustomer(customerId).createAddress(addressId)));
    }

    @Operation(summary = "删除顾客的收货地址对象")
    @DeleteMapping("/customers/{customer_id}/addresses/{address_id}")
    public void removeAddress(@PathVariable("customer_id") String id,
                              @PathVariable("address_id") String addressId) {
        this.customerService.removeAddress(id, addressId);
    }

    @Operation(summary = "获得顾客的收货地址对象集合")
    @GetMapping("/customers/{customer_id}/addresses")
    public List<CustomerAddress> getAddresses(@PathVariable("customer_id") String id) {
        return this.customerService.getAddresses(id);
    }

    @Operation(summary = "根据标识获得顾客的收货地址对象")
    @GetMapping("/customers/{customer_id}/addresses/{address_id}")
    public Optional<CustomerAddress> getAddress(@PathVariable("customer_id") String customerId,
                                                @PathVariable("address_id") String addressId) {
        return this.customerService.getAddress(customerId, addressId);
    }

    @Operation(summary = "根据标识获得顾客的默认收货地址对象")
    @GetMapping("/customers/{customer_id}/addresses/default")
    public Optional<CustomerAddress> getDefaultAddress(@PathVariable("customer_id") String id) {
        return this.customerService.getDefaultAddress(id);
    }
}
