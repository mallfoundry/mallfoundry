package org.mallfoundry.shipping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AddressSupport implements Address {

    private String firstName;

    private String lastName;

    private String countryCode;

    private String mobile;

    private String zip;

    private String address;

    private String location;
}
