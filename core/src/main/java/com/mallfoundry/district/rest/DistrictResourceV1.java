package com.mallfoundry.district.rest;

import com.mallfoundry.district.DistrictService;
import com.mallfoundry.district.Province;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/district")
public class DistrictResourceV1 {

    private final DistrictService districtService;

    public DistrictResourceV1(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/countries/{country_id}/provinces")
    public List<Province> getProvinces(@PathVariable("country_id") String countryId) {
        return this.districtService.getProvinces(countryId);
    }

//    @GetMapping("/v1/countries/{country_code}/provinces/province_code")
//    public List<Province> getProvinces(
//            @PathVariable("country_code") String countryCode) {
//        return this.districtService.getProvinces(countryCode);
//    }
}
