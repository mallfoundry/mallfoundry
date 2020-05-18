package com.mallfoundry.customer;

import java.util.Date;

public interface ShippingAddress {

    String getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getZipCode();

    void setZipCode(String zipCode);

    String getLocation();

    void setLocation(String location);

    String getAddress();

    void setAddress(String address);

    boolean isDefaulted();

    void setDefaulted(boolean defaulted);

    Date getCreatedTime();
}
