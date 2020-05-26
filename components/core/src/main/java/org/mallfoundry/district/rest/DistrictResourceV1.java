package org.mallfoundry.district.rest;

import org.mallfoundry.district.City;
import org.mallfoundry.district.County;
import org.mallfoundry.district.InternalDistrictService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/district")
public class DistrictResourceV1 {

    private final InternalDistrictService districtService;

    private final RestDistrictService restDistrictService;

    public DistrictResourceV1(InternalDistrictService districtService,
                              RestDistrictService restDistrictService) {
        this.districtService = districtService;
        this.restDistrictService = restDistrictService;
    }

    @GetMapping("/countries/{country_id}/regions")
    public List<RegionResponse> getRegions(@PathVariable("country_id") String countryId,
                                           @RequestParam(required = false, defaultValue = "0") byte scope) {
        return this.restDistrictService.getRegions(
                this.districtService.createQuery().toBuilder().countryId(countryId).scope(scope).build());
    }

    @GetMapping("/countries/{country_id}/provinces")
    public List<ProvinceResponse> getProvinces(@PathVariable("country_id") String countryId,
                                               @RequestParam(required = false, defaultValue = "0") byte scope) {
        return this.restDistrictService.getProvinces(
                this.districtService.createQuery().toBuilder().countryId(countryId).scope(scope).build());
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
