package org.mallfoundry.district;

import java.util.List;

public interface DistrictService {

    DistrictQuery createQuery();

    Region createRegion(String code, String name, String countryId);

    Province createProvince(String code, String name, String countryId);

    City createCity(String code, String name, String provinceId);

    County createCounty(String code, String name, String cityId);

    Region addRegion(Region region);

    Province addProvince(Province province);

    City addCity(City city);

    County addCounty(County county);

    Region updateRegion(Region region);

    Province updateProvince(Province province);

    City updateCity(City city);

    County updateCounty(County county);

    List<Region> getRegions(DistrictQuery query);

    List<Province> getProvinces(DistrictQuery query);

    List<City> getCities(String provinceId);

    List<County> getCounties(String cityId);
}
