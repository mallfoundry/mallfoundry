package com.mallfoundry.district;

import java.util.List;

public interface DistrictService {

    DistrictQuery createQuery();

    Region createRegion(String code, String name, String countryId);

    Province createProvince(String code, String name, String countryId);

    City createCity(String code, String name, String provinceId);

    County createCounty(String code, String name, String cityId);

    Region saveRegion(Region region);

    Province saveProvince(Province province);

    City saveCity(City city);

    County saveCounty(County county);

    List<Region> getRegions(DistrictQuery query);

    List<Province> getProvinces(DistrictQuery query);

    List<City> getCities(String provinceId);

    List<County> getCounties(String cityId);
}
