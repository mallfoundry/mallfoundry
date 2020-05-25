package org.mallfoundry.store;

import java.util.Date;

public interface Location {

    String getId();

    boolean isDefaulted();

    void setDefaulted(boolean defaulted);

    boolean isActive();

    void setActive(boolean active);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getZip();

    void setZip(String zip);

    String getLocation();

    void setLocation(String location);

    String getAddress();

    void setAddress(String address);

    Date getCreatedTime();
}
