package com.mallfoundry.district.rest;

import com.mallfoundry.district.DistrictService;
import com.mallfoundry.district.Province;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/district/countries/")
public class DistrictResourceV1 {

    private final DistrictService districtService;

    public DistrictResourceV1(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("{country_code}/provinces")
    public List<Province> getProvinces(@PathVariable("country_code") String countryCode) {
        return this.districtService.getProvinces(countryCode);
    }

//    @GetMapping("/v1/countries/{country_code}/provinces/province_code")
//    public List<Province> getProvinces(
//            @PathVariable("country_code") String countryCode) {
//        return this.districtService.getProvinces(countryCode);
//    }
}
