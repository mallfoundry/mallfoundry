package org.mallfoundry.analytics.stream.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OrderQuantityFact {
    private String storeId;
    private String dateId;
    private String productId;
    private String variantId;
    private String customerId;
    private int pendingQuantity;
    private int awaitingPaymentQuantity;
    private int awaitingFulfillmentQuantity;
    private int awaitingShipmentQuantity;
    private int partiallyShippedQuantity;
    private int shippedQuantity;
    private int awaitingPickupQuantity;
    private int partiallyRefundedQuantity;
    private int refundedQuantity;
    private int disputedQuantity;
    private int cancelledQuantity;
    private int completedQuantity;
    private int declinedQuantity;

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
