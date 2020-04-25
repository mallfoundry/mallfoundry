package com.mallfoundry.district.rest;

import com.mallfoundry.district.City;
import com.mallfoundry.district.County;
import com.mallfoundry.district.InternalDistrictService;
import com.mallfoundry.district.Province;
import com.mallfoundry.district.Region;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/district")
public class DistrictResourceV1 {

    private final InternalDistrictService districtService;

    public DistrictResourceV1(InternalDistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/countries/{country_id}/regions")
    public List<Region> getRegions(@PathVariable("country_id") String countryId) {
        return this.districtService.getRegions(countryId);
    }

    @GetMapping("/countries/{country_id}/provinces")
    public List<Province> getProvinces(@PathVariable("country_id") String countryId) {
        return this.districtService.getProvinces(countryId);
    }

    @GetMapping("/countries/{country_id}/provinces/{province_id}/cities")
    public List<City> getCities(
            @PathVariable("country_id") String countryId,
            @PathVariable("province_id") String provinceId) {
        Assert.notNull(countryId, "Country id cannot be null");
        return this.districtService.getCities(provinceId);
    }

    @GetMapping("/countries/{country_id}/provinces/{province_id}/cities/{city_id}")
    public List<County> getCounties(
            @PathVariable("country_id") String countryId,
            @PathVariable("province_id") String provinceId,
            @PathVariable("city_id") String cityId) {
        Assert.notNull(countryId, "Country id cannot be null");
        Assert.notNull(provinceId, "Province id cannot be null");
        return this.districtService.getCounties(cityId);
    }
}
