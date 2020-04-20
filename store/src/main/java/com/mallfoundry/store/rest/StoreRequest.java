package com.mallfoundry.store.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {
    @JsonProperty("logo_url")
    private String logoUrl;
    private String description;
}
