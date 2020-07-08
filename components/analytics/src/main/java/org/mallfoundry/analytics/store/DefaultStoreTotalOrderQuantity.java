package org.mallfoundry.analytics.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultStoreTotalOrderQuantity implements StoreTotalOrderQuantity {
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
}
