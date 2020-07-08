package org.mallfoundry.rest.analytics.store;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.analytics.store.StoreTotalOrderQuantity;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class StoreTotalOrderQuantityResponse {
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

    public static StoreTotalOrderQuantityResponse of(StoreTotalOrderQuantity quantity) {
        var response = new StoreTotalOrderQuantityResponse();
        BeanUtils.copyProperties(quantity, response);
        return response;
    }
}
