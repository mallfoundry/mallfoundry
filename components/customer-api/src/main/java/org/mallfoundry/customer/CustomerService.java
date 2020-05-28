package org.mallfoundry.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(String customerId);

    Optional<Customer> getCustomer(String customerId);

    Customer addCustomer(Customer customer);

    void setCustomer(Customer customer);

    void deleteCustomer(String customerId);

    ShippingAddress addAddress(String customerId, ShippingAddress address);

    List<ShippingAddress> getAddresses(String customerId);

    Optional<ShippingAddress> getAddress(String customerId, String addressId);

    Optional<ShippingAddress> getDefaultAddress(String customerId);

    void setAddress(String customerId, ShippingAddress address);

    void removeAddress(String customerId, String addressId);

}
