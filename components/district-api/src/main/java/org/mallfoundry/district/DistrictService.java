package org.mallfoundry.district;

import java.util.List;
import java.util.Optional;

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

    List<Region> getRegions(DistrictQuery query);

    List<Province> getProvinces(DistrictQuery query);

    List<City> getCities(DistrictQuery query);

    List<County> getCounties(DistrictQuery query);

    Optional<County> getCounty(String id);
}
