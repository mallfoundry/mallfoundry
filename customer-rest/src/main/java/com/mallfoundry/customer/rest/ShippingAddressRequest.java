package com.mallfoundry.customer.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingAddressRequest {
    private String firstName;
    private String lastName;
    private String countryCode;
    private String mobile;
    private String zipCode;
    private String location;
    private String address;
    private boolean defaulted;
}
