package org.mallfoundry.analytics.stream.order.repository.jpa;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class JpaOrderQuantityFactId implements Serializable {
    private String storeId;
    private String dateId;
    private String productId;
    private String variantId;
    private String customerId;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaOrderQuantityFactId)) {
            return false;
        }
        JpaOrderQuantityFactId that = (JpaOrderQuantityFactId) object;
        return Objects.equals(storeId, that.storeId)
                && Objects.equals(dateId, that.dateId)
                && Objects.equals(productId, that.productId)
                && Objects.equals(variantId, that.variantId)
                && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, dateId, productId, variantId, customerId);
    }
}
