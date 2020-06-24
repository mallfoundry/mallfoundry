package org.mallfoundry.shipping;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tang Zhi
 * @since 1.0
 */
@Getter
@Setter
public class DefaultAddress extends AddressSupport {

    private String id;

    private String firstName;

    private String lastName;

    private String countryCode;

    private String mobile;

    private String zip;

    private String address;

    private String provinceId;

    private String province;

    private String cityId;

    private String city;

    private String countyId;

    private String county;
}
