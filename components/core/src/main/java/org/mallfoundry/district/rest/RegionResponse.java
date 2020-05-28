package org.mallfoundry.district.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mallfoundry.district.Region;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class RegionResponse {

    @JsonIgnore
    private final Region region;

    @Getter
    private List<ProvinceResponse> provinces;

    public RegionResponse(Region region, int scope) {
        this.region = region;
        if (scope > 0) {
            this.provinces = region
                    .getProvinces()
                    .stream()
                    .map(province -> new ProvinceResponse(province, scope))
                    .collect(Collectors.toList());
        }
    }

    public String getCountryId() {
        return this.region.getCountryId();
    }

    public String getId() {
        return this.region.getId();
    }

    public String getCode() {
        return this.region.getCode();
    }

    public String getName() {
        return this.region.getName();
    }

    public long getPosition() {
        return this.region.getPosition();
    }
}
