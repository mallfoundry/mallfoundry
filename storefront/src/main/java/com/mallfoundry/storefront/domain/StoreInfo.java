package com.mallfoundry.storefront.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInfo {

    private String id;

    private String name;

    @JsonProperty("logo_url")
    private String logoUrl;
}
