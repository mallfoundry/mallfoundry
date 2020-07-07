package org.mallfoundry.analytics.stream.order.repository.jpa;

import org.mallfoundry.analytics.stream.models.OrderQuantityFact;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "mf_dw_order_quantity_fact")
@IdClass(JpaOrderQuantityFactId.class)
public class JpaOrderQuantityFact extends OrderQuantityFact {

    @Id
    @Column(name = "customer_id_")
    @Override
    public String getCustomerId() {
        return super.getCustomerId();
    }

    @Id
    @Column(name = "date_id_")
    @Override
    public String getDateId() {
        return super.getDateId();
    }

    @Id
    @Column(name = "product_id_")
    @Override
    public String getProductId() {
        return super.getProductId();
    }

    @Id
    @Column(name = "variant_id_")
    @Override
    public String getVariantId() {
        return super.getVariantId();
    }

    @Id
    @Column(name = "store_id_")
    @Override
    public String getStoreId() {
        return super.getStoreId();
    }

    @Column(name = "awaiting_payment_quantity_")
    @Override
    public int getAwaitingPaymentQuantity() {
        return super.getAwaitingPaymentQuantity();
    }

    @Column(name = "awaiting_shipment_quantity_")
    @Override
    public int getAwaitingShipmentQuantity() {
        return super.getAwaitingShipmentQuantity();
    }

    @Column(name = "disputed_quantity_")
    @Override
    public int getDisputedQuantity() {
        return super.getDisputedQuantity();
    }

    @Column(name = "shipped_quantity_")
    @Override
    public int getShippedQuantity() {
        return super.getShippedQuantity();
    }

    @Column(name = "awaiting_fulfillment_quantity_")
    @Override
    public int getAwaitingFulfillmentQuantity() {
        return super.getAwaitingFulfillmentQuantity();
    }

    @Column(name = "awaiting_pickup_quantity")
    @Override
    public int getAwaitingPickupQuantity() {
        return super.getAwaitingPickupQuantity();
    }

    @Column(name = "cancelled_quantity_")
    @Override
    public int getCancelledQuantity() {
        return super.getCancelledQuantity();
    }

    @Column(name = "completed_quantity_")
    @Override
    public int getCompletedQuantity() {
        return super.getCompletedQuantity();
    }

    @Column(name = "declined_quantity_")
    @Override
    public int getDeclinedQuantity() {
        return super.getDeclinedQuantity();
    }

    @Column(name = "partially_refunded_quantity_")
    @Override
    public int getPartiallyRefundedQuantity() {
        return super.getPartiallyRefundedQuantity();
    }

    @Column(name = "partially_shipped_quantity_")
    @Override
    public int getPartiallyShippedQuantity() {
        return super.getPartiallyShippedQuantity();
    }

    @Column(name = "pending_quantity_")
    @Override
    public int getPendingQuantity() {
        return super.getPendingQuantity();
    }

    @Column(name = "refunded_quantity_")
    @Override
    public int getRefundedQuantity() {
        return super.getRefundedQuantity();
    }

    public static JpaOrderQuantityFact of(OrderQuantityFact fact) {
        if (fact instanceof JpaOrderQuantityFact) {
            return (JpaOrderQuantityFact) fact;
        }
        var target = new JpaOrderQuantityFact();
        BeanUtils.copyProperties(fact, target);
        return target;
    }
}
