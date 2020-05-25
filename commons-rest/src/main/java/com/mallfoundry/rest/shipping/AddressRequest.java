package com.mallfoundry.rest.shipping;

import com.mallfoundry.shipping.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class AddressRequest {

    private String firstName;

    private String lastName;

    private String countryCode;

    private String mobile;

    private String zip;

    private String address;

    private String location;

    public void assignToAddress(Address address) {
        BeanUtils.copyProperties(this, address);
    }

}
