package com.mallfoundry.rest.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {

    private String id;

    private String name;

    @JsonProperty("logo_url")
    private String logoUrl;

    private String description;
}
