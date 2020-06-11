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

    private String countryCode;

    private String mobile;

    private String zip;

    private String address;

    private String location;

    public void assignToAddress(Address address) {
        BeanUtils.copyProperties(this, address);
    }

}
