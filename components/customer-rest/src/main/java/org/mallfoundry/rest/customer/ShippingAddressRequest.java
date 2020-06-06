package org.mallfoundry.rest.customer;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.customer.CustomerAddress;

@Getter
@Setter
public class ShippingAddressRequest {

    private String firstName;

    private String lastName;

    private String countryCode;

    private String mobile;

    private String zip;

    private String location;

    private String address;

    private boolean defaulted;

    CustomerAddress assignToAddress(CustomerAddress address) {
        return address.toBuilder().firstName(this.firstName)
                .lastName(this.lastName)
                .countryCode(this.countryCode)
                .address(this.address)
                .location(this.location)
                .mobile(this.mobile)
                .zip(this.zip)
                .defaulted(this.defaulted).build();
    }
}
