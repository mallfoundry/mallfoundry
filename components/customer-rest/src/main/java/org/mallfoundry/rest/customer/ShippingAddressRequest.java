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

    private String provinceId;

    private String province;

    private String cityId;

    private String city;

    private String countyId;

    private String county;

    private String address;

    private boolean defaulted;

    CustomerAddress assignToAddress(CustomerAddress address) {
        return address.toBuilder().firstName(this.firstName)
                .lastName(this.lastName)
                .countryCode(this.countryCode)
                .provinceId(this.provinceId).province(this.province)
                .cityId(this.cityId).city(this.city)
                .countyId(this.countyId).county(this.county)
                .address(this.address)
                .mobile(this.mobile)
                .zip(this.zip)
                .defaulted(this.defaulted).build();
    }
}
