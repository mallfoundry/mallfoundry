package com.mallfoundry.order;

import java.io.Serializable;

public interface AddressBase extends Serializable {

    String getConsignee();

    void setConsignee(String consignee);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getPostalCode();

    void setPostalCode(String postalCode);

    String getAddress();

    void setAddress(String address);

    String getLocation();

    void setLocation(String location);
}
