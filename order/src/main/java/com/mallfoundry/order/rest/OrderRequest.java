package com.mallfoundry.order.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    @JsonProperty("staff_notes")
    private String staffNotes;
}
