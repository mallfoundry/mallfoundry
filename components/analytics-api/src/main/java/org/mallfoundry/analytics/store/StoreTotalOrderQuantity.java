package org.mallfoundry.analytics.store;

public interface StoreTotalOrderQuantity {

    int getPendingQuantity();

    int getAwaitingPaymentQuantity();

    int getAwaitingFulfillmentQuantity();

    int getAwaitingShipmentQuantity();

    int getPartiallyShippedQuantity();

    int getShippedQuantity();

    int getAwaitingPickupQuantity();

    int getPartiallyRefundedQuantity();

    int getRefundedQuantity();

    int getDisputedQuantity();

    int getCancelledQuantity();

    int getCompletedQuantity();

    int getDeclinedQuantity();
}
