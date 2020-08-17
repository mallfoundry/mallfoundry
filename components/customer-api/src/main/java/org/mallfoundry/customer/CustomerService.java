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

package org.mallfoundry.customer;

import org.mallfoundry.identity.User;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerId createCustomerId(String tenantId, String customerId);

    Customer createCustomer(User user);

    Customer createCustomer(CustomerId customerId);

    Customer addCustomer(Customer customer);

    Optional<Customer> findCustomer(String customerId);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(String customerId);

    CustomerAddress addCustomerAddress(String customerId, CustomerAddress address);

    List<CustomerAddress> getCustomerAddresses(String customerId);

    Optional<CustomerAddress> findCustomerAddress(String customerId, String addressId);

    CustomerAddress getDefaultCustomerAddress(String customerId);

    Optional<CustomerAddress> findDefaultCustomerAddress(String customerId);

    void updateCustomerAddress(String customerId, CustomerAddress address);

    void removeCustomerAddress(String customerId, String addressId);
}
