package org.mallfoundry.district.rest;

import org.mallfoundry.district.DistrictQuery;
import org.mallfoundry.district.DistrictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestDistrictService {

    private final DistrictService districtService;

    public RestDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    @Transactional
    public List<RegionResponse> getRegions(DistrictQuery query) {
        return this.districtService.getRegions(query)
                .stream()
                .map(region -> new RegionResponse(region, query.getScope()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProvinceResponse> getProvinces(DistrictQuery query) {
        return this.districtService.getProvinces(query)
                .stream()
                .map(province -> new ProvinceResponse(province, query.getScope()))
                .collect(Collectors.toList());
    }
}
