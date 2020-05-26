package org.mallfoundry.district.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mallfoundry.district.County;

public class CountyResponse {

    @JsonIgnore
    private final County county;

    public CountyResponse(County county) {
        this.county = county;
    }

    public String getCityId() {
        return this.county.getCityId();
    }

    public String getId() {
        return this.county.getId();
    }

    public String getCode() {
        return this.county.getCode();
    }

    public String getName() {
        return this.county.getName();
    }

    public Integer getPosition() {
        return this.county.getPosition();
    }
}
