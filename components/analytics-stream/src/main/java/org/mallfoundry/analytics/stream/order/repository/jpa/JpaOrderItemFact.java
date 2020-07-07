package org.mallfoundry.analytics.stream.order.repository.jpa;

import org.mallfoundry.analytics.stream.models.OrderItemFact;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mf_dw_order_item_fact")
public class JpaOrderItemFact extends OrderItemFact {

    public static JpaOrderItemFact of(OrderItemFact fact) {
        if (fact instanceof JpaOrderItemFact) {
            return (JpaOrderItemFact) fact;
        }
        var target = new JpaOrderItemFact();
        BeanUtils.copyProperties(fact, target);
        return target;
    }

    @Column(name = "created_date_id_")
    @Override
    public String getCreatedDateId() {
        return super.getCreatedDateId();
    }

    @Column(name = "customer_id_")
    @Override
    public String getCustomerId() {
        return super.getCustomerId();
    }

    @Id
    @Column(name = "id_")
    @Override
    public String getId() {
        return super.getId();
    }

    @Column(name = "order_id_")
    @Override
    public String getOrderId() {
        return super.getOrderId();
    }

    @Column(name = "price_")
    @Override
    public BigDecimal getPrice() {
        return super.getPrice();
    }

    @Column(name = "product_id_")
    @Override
    public String getProductId() {
        return super.getProductId();
    }

    @Column(name = "quantity_")
    @Override
    public int getQuantity() {
        return super.getQuantity();
    }

    @Column(name = "shipping_cost_")
    @Override
    public BigDecimal getShippingCost() {
        return super.getShippingCost();
    }

    @Column(name = "status_id_")
    @Override
    public String getStatusId() {
        return super.getStatusId();
    }

    @Column(name = "store_id_")
    @Override
    public String getStoreId() {
        return super.getStoreId();
    }

    @Column(name = "subtotal_amount_")
    @Override
    public BigDecimal getSubtotalAmount() {
        return super.getSubtotalAmount();
    }

    @Column(name = "total_amount_")
    @Override
    public BigDecimal getTotalAmount() {
        return super.getTotalAmount();
    }

    @Column(name = "variant_id_")
    @Override
    public String getVariantId() {
        return super.getVariantId();
    }
}
