package org.mallfoundry.dw;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQuantityFact {
    private String storeId;
    private String brandId;
    private String categoryId;
    private String statusId;
    private int quantity;
}
