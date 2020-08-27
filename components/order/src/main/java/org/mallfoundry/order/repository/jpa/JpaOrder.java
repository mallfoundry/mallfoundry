/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.order.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.inventory.InventoryDeduction;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.order.OrderStatus;
import org.mallfoundry.order.OrderSupport;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.aftersales.repository.jpa.JpaOrderRefund;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.order.shipping.repository.jpa.JpaOrderShipment;
import org.mallfoundry.payment.PaymentMethod;
import org.mallfoundry.payment.PaymentStatus;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.repository.jpa.convert.AddressConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mallfoundry.order.OrderStatus.INCOMPLETE;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_order")
public class JpaOrder extends OrderSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private OrderStatus status = INCOMPLETE;

    @NotBlank
    @Column(name = "tenant_id_")
    private String tenantId;

    @NotBlank
    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "store_name_")
    private String storeName;

    @NotBlank
    @Column(name = "customer_id_")
    private String customerId;

    @Column(name = "source_name_")
    private String sourceName;

    @NotNull
    @Column(name = "source_")
    private OrderSource source;

    @Column(name = "customer_message_")
    private String customerMessage;

    @Column(name = "staff_notes_")
    private String staffNotes;

    @Column(name = "staff_stars_")
    private Integer staffStars;

    @NotNull
    @Convert(converter = AddressConverter.class)
    @Column(name = "shipping_address_")
    private Address shippingAddress;

    @Valid
    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = JpaOrderItem.class)
    @JoinColumn(name = "order_id_")
    @OrderBy("id ASC")
    private List<OrderItem> items = new ArrayList<>();

    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = JpaOrderShipment.class)
    @JoinColumn(name = "order_id_")
    @OrderBy("shippedTime ASC")
    private List<OrderShipment> shipments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_status_")
    private OrderStatus refundStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = JpaOrderRefund.class)
    @JoinColumn(name = "order_id_")
    @OrderBy("appliedTime ASC")
    private List<OrderRefund> refunds = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status_")
    private OrderStatus reviewStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = JpaOrderReview.class)
    @JoinColumn(name = "order_id_")
    private List<OrderReview> reviews = new ArrayList<>();

    @Column(name = "shipped_items_")
    private int shippedItems;

    @Column(name = "payment_id_")
    private String paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status_")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_")
    private PaymentMethod paymentMethod;

    @Column(name = "inventory_deduction_")
    private InventoryDeduction inventoryDeduction;

    @Column(name = "sign_message_")
    private String signMessage;

    @Column(name = "cancel_reason_")
    private String cancelReason;

    @Column(name = "close_reason_")
    private String closeReason;

    @Column(name = "decline_reason_")
    private String declineReason;

    @Column(name = "placed_time_")
    private Date placedTime;

    @Column(name = "placing_expires_")
    private int placingExpires;

    @Column(name = "placing_expired_time_")
    private Date placingExpiredTime;

    @Column(name = "paid_time_")
    private Date paidTime;

    @Column(name = "fulfilled_time_")
    private Date fulfilledTime;

    @Column(name = "shipped_time_")
    private Date shippedTime;

    @Column(name = "signed_time_")
    private Date signedTime;

    @Column(name = "received_time_")
    private Date receivedTime;

    @Column(name = "cancelled_time_")
    private Date cancelledTime;

    @Column(name = "closed_time_")
    private Date closedTime;

    @Column(name = "declined_time_")
    private Date declinedTime;

    @Column(name = "refunded_time_")
    private Date refundedTime;

    public JpaOrder(String id) {
        this.setId(id);
    }

    public static JpaOrder of(Order order) {
        if (order instanceof JpaOrder) {
            return (JpaOrder) order;
        }

        var target = new JpaOrder();
        BeanUtils.copyProperties(order, target);
        return target;
    }

    @Override
    public OrderItem createItem(String itemId) {
        return new JpaOrderItem(itemId);
    }

    @Override
    public OrderShipment createShipment(String shipmentId) {
        return new JpaOrderShipment(shipmentId).toBuilder().shippingAddress(this.getShippingAddress()).build();
    }

    @Override
    public OrderRefund createRefund(String refundId) {
        return new JpaOrderRefund(this.getId(), refundId);
    }

    @Override
    public OrderReview createReview(String reviewId) {
        return new JpaOrderReview(reviewId);
    }
}
