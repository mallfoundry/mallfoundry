package org.mallfoundry.district;

import java.util.List;

public interface Region extends District {

    List<Province> getProvinces();

    void setProvinces(List<Province> provinces);

    String getCountryId();

    void setCountryId(String countryId);
}
