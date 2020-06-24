package org.mallfoundry.rest.shipping;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.shipping.Address;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class AddressRequest {

    private String id;

    private String firstName;

    private String lastName;

    private String mobile;

    private String zip;

    private String countryCode;

    private String provinceId;

    private String province;

    private String cityId;

    private String city;

    private String countyId;

    private String county;

    private String address;

    public void assignToAddress(Address address) {
        BeanUtils.copyProperties(this, address);
    }

}
