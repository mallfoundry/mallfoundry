package org.mallfoundry.rest.customer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BrowsingProductRequest {

    private String id;

    private String name;

    private BigDecimal price;
}
