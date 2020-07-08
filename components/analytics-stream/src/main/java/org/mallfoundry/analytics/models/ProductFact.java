package org.mallfoundry.analytics.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFact {
    private String id;
    private String storeId;
    private String brandId;
    private String categoryId;
    private String statusId;
}
