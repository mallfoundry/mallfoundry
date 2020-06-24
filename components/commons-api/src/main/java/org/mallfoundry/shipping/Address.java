package org.mallfoundry.shipping;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;

/**
 * @author Tang Zhi
 * @since 1.0
 */
public interface Address extends Serializable {

    String getId();

    void setId(String id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getMobile();

    void setMobile(String mobile);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getProvinceId();

    void setProvinceId(String provinceId);

    String getProvince();

    void setProvince(String province);

    String getCityId();

    void setCityId(String cityId);

    String getCity();

    void setCity(String city);

    String getCountyId();

    void setCountyId(String countyId);

    String getCounty();

    void setCounty(String county);

    String getAddress();

    void setAddress(String address);

    String getZip();

    void setZip(String zip);

    Builder toBuilder();

    interface Builder extends ObjectBuilder<Address> {

        Builder firstName(String firstName);

        Builder lastName(String lastName);

        Builder mobile(String mobile);

        Builder zip(String zip);

        Builder countryCode(String countryCode);

        Builder provinceId(String provinceId);

        Builder province(String province);

        Builder cityId(String cityId);

        Builder city(String city);

        Builder countyId(String countyId);

        Builder county(String county);

        Builder address(String address);
    }
}
