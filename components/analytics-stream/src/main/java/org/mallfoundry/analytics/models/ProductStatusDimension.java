package org.mallfoundry.analytics.models;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.product.ProductStatus;

@Getter
@Setter
public class ProductStatusDimension {

    private String id;

    private String label;

    public static String idOf(ProductStatus status) {
        return status.toString();
    }
}
