package com.mallfoundry.district.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mallfoundry.district.City;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class CityResponse {

    @JsonIgnore
    private final City city;

    @Getter
    private List<CountyResponse> counties;

    public CityResponse(City city, int scope) {
        this.city = city;
        if (scope > 0) {
            this.counties = city
                    .getCounties()
                    .stream()
                    .map(CountyResponse::new)
                    .collect(Collectors.toList());
        }
    }

    public String getProvinceId() {
        return this.city.getProvinceId();
    }

    public String getId() {
        return this.city.getId();
    }

    public String getCode() {
        return this.city.getCode();
    }

    public String getName() {
        return this.city.getName();
    }

    public Integer getPosition() {
        return this.city.getPosition();
    }
}
