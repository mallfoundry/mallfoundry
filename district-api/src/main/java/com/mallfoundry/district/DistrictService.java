package com.mallfoundry.district;

import java.util.List;

public interface DistrictService {

    Province createProvince(String code, String name, String countryId);

    City createCity(String code, String name, String provinceId);

    County createCounty(String code, String name, String cityId);

    Province saveProvince(Province province);

    City saveCity(City city);

    County saveCounty(County county);

    List<Province> getProvinces(String countryId);

    List<City> getCities(String provinceId);

    List<County> getCounties(String cityId);
}
