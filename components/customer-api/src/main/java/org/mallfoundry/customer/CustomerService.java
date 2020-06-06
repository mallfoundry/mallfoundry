package org.mallfoundry.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(String customerId);

    Optional<Customer> getCustomer(String customerId);

    Customer addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(String customerId);

    CustomerAddress addAddress(String customerId, CustomerAddress address);

    List<CustomerAddress> getAddresses(String customerId);

    Optional<CustomerAddress> getAddress(String customerId, String addressId);

    Optional<CustomerAddress> getDefaultAddress(String customerId);

    void setAddress(String customerId, CustomerAddress address);

    void removeAddress(String customerId, String addressId);

}
