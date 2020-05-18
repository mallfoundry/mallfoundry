package com.mallfoundry.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(String userId);

    ShippingAddress createShippingAddress();

    Optional<Customer> getCustomer(String customerId);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(String customerId);

    void addShippingAddress(String customerId, ShippingAddress address);

    List<ShippingAddress> getShippingAddresses(String customerId);

    Optional<ShippingAddress> getShippingAddress(String customerId, String addressId);

    Optional<ShippingAddress> getDefaultShippingAddress(String customerId);

    void saveShippingAddress(String customerId, ShippingAddress address);

    void removeShippingAddress(String customerId, String addressId);

}
