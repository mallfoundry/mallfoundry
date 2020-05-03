package com.mallfoundry.district.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mallfoundry.district.Province;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ProvinceResponse {

    @JsonIgnore
    private final Province province;

    @Getter
    private List<CityResponse> cities;

    public ProvinceResponse(Province province, int scope) {
        this.province = province;
        if (scope > 0) {
            this.cities = province
                    .getCities()
                    .stream()
                    .map(city -> new CityResponse(city, scope - 1))
                    .collect(Collectors.toList());
        }
    }

    public String getCountryId() {
        return this.province.getCountryId();
    }

    public String getId() {
        return this.province.getId();
    }

    public String getCode() {
        return this.province.getCode();
    }

    public String getName() {
        return this.province.getName();
    }

    public Integer getPosition() {
        return this.province.getPosition();
    }
}
