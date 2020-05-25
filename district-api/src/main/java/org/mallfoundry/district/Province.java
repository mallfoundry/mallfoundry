package org.mallfoundry.district;

import java.util.List;

public interface Province extends District {

    String getCountryId();

    void setCountryId(String countryId);

    List<City> getCities();

    void setCities(List<City> cities);
}
