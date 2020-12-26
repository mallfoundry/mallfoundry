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

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.customer.Customer;
import org.mallfoundry.customer.CustomerAddress;
import org.mallfoundry.customer.CustomerService;
import org.mallfoundry.identity.TenantOwnership;
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


@Tag(name = "Customers")
@RequestMapping("/v1")
@RestController
public class CustomerResourceV1 {

    private final CustomerService customerService;

    public CustomerResourceV1(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public Optional<CustomerResponse> findCustomer() {
        return this.customerService.findCustomer(SubjectHolder.getSubject().getId()).map(CustomerResponse::new);
    }

    private Customer createCustomer(String customerId) {
        return this.customerService.createCustomer(
                this.customerService.createCustomerId(TenantOwnership.DEFAULT_TENANT_ID, customerId));
    }

    @GetMapping("/customers/{customer_id}")
    public Optional<CustomerResponse> findCustomer(@PathVariable("customer_id") String id) {
        return this.customerService.findCustomer(id).map(CustomerResponse::new);
    }

    @PatchMapping("/customers/{customer_id}")
    public Optional<CustomerResponse> updateCustomer(@PathVariable("customer_id") String customerId,
                                                     @RequestBody CustomerRequest request) {
        return Optional.ofNullable(
                this.customerService.updateCustomer(
                        request.assignToCustomer(
                                this.createCustomer(customerId))))
                .map(CustomerResponse::new);
    }

    @PostMapping("/customers/{customer_id}/addresses")
    public CustomerAddress addCustomerAddress(@PathVariable("customer_id") String customerId,
                                              @RequestBody ShippingAddressRequest request) {
        return this.customerService.addCustomerAddress(customerId,
                request.assignTo(
                        this.createCustomer(customerId).createAddress(null)));
    }

    @PatchMapping("/customers/{customer_id}/addresses/{address_id}")
    public void updateCustomerAddress(@PathVariable("customer_id") String customerId,
                                      @PathVariable("address_id") String addressId,
                                      @RequestBody ShippingAddressRequest request) {
        this.customerService.updateCustomerAddress(customerId,
                request.assignTo(
                        this.createCustomer(customerId).createAddress(addressId)));
    }

    @DeleteMapping("/customers/{customer_id}/addresses/{address_id}")
    public void removeCustomerAddress(@PathVariable("customer_id") String id,
                                      @PathVariable("address_id") String addressId) {
        this.customerService.removeCustomerAddress(id, addressId);
    }

    @GetMapping("/customers/{customer_id}/addresses")
    public List<CustomerAddress> getCustomerAddresses(@PathVariable("customer_id") String id) {
        return this.customerService.getCustomerAddresses(id);
    }

    @GetMapping("/customers/{customer_id}/addresses/{address_id}")
    public Optional<CustomerAddress> findCustomerAddress(@PathVariable("customer_id") String customerId,
                                                         @PathVariable("address_id") String addressId) {
        return this.customerService.findCustomerAddress(customerId, addressId);
    }

    @GetMapping("/customers/{customer_id}/addresses/default")
    public Optional<CustomerAddress> findDefaultCustomerAddress(@PathVariable("customer_id") String id) {
        return this.customerService.findDefaultCustomerAddress(id);
    }
}
