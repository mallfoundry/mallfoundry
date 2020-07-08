package org.mallfoundry.analytics.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OrderQuantityFact {
    private String storeId;
    private String customerId;
    private String productId;
    private String variantId;
    private String statusId;
    private int quantity;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OrderQuantityFact)) {
            return false;
        }
        OrderQuantityFact that = (OrderQuantityFact) object;
        return Objects.equals(storeId, that.storeId)
                && Objects.equals(productId, that.productId)
                && Objects.equals(variantId, that.variantId)
                && Objects.equals(customerId, that.customerId)
                && Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, productId, variantId, customerId, statusId);
    }
}
