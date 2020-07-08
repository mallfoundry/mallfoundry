package org.mallfoundry.analytics.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemFact {
    private String id;
    private String orderId;
    private String storeId;
    private String customerId;
    private String productId;
    private String variantId;
    private String statusId;
    private String createdDateId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal shippingCost;
    private BigDecimal subtotalAmount;
    private BigDecimal totalAmount;
}
