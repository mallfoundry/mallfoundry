package org.mallfoundry.shipping;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tang Zhi
 * @since 1.0
 */
@Getter
@Setter
public abstract class AddressSupport implements Address {

    private String id;

    private String firstName;

    private String lastName;

    private String countryCode;

    private String mobile;

    private String zip;

    private String address;

    private String location;
}
