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

package org.mallfoundry.order;

import org.mallfoundry.customer.CustomerOwnership;
import org.mallfoundry.inventory.InventoryDeduction;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.aftersales.OrderRefundException;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.finance.PaymentMethod;
import org.mallfoundry.finance.PaymentStatus;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.store.StoreOwnership;
import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 商品订单对象。
 *
 * @author Zhi Tang
 */
public interface Order extends StoreOwnership, CustomerOwnership, ObjectBuilder.ToBuilder<Order.Builder> {

    OrderId toId();

    String getId();

    void setId(String id);

    /**
     * 订单收货地址对象。
     *
     * @return 收货地址对象
     */
    Address getShippingAddress();

    void setShippingAddress(Address shippingAddress);

    String getStoreName();

    void setStoreName(String storeName);

    String getCustomerMessage();

    void setCustomerMessage(String customerMessage);

    OrderStatus getStatus();

    /**
     * 商家员工备注。
     *
     * @return 备注信息
     */
    String getStaffNotes();

    void setStaffNotes(String staffNotes);

    Integer getStaffStars();

    void setStaffStars(Integer staffStars);

    String getSourceName();

    void setSourceName(String sourceName);

    OrderSource getSource();

    void setSource(OrderSource source);

    OrderItem createItem(String itemId);

    void addItem(OrderItem item);

    OrderItem getItem(String itemId);

    List<OrderItem> getItems(List<String> itemIds);

    List<OrderItem> getItems();

    void setItems(List<OrderItem> items);

    int getItemsSize();

    OrderShipment createShipment(String shipmentId);

    void addShipment(OrderShipment shipment);

    List<OrderShipment> getShipments();

    Optional<OrderShipment> findShipment(String shipmentId);

    OrderShipment getShipment(String shipmentId);

    List<OrderShipment> getShipments(Set<String> shipmentIds);

    void updateShipment(OrderShipment shipment);

    void updateShipments(List<OrderShipment> shipments);

    void removeShipment(OrderShipment shipment);

    void removeShipments(List<OrderShipment> shipments);

    int getShippedItems();

    OrderStatus getDisputeStatus();

    /**
     * 创建一个订单退款对象。
     *
     * @param refundId 订单退款对象标识
     * @return 一个新的订单退款对象
     */
    OrderRefund createRefund(String refundId);

    /**
     * 获得与此订单对象所关联的所有的订单退款对象集合。
     *
     * @return 订单退款对象集合
     */
    List<OrderRefund> getRefunds();

    Optional<OrderRefund> findRefund(String refundId);

    /**
     * 根据订单退款对象标识获得订单退款对象。
     *
     * @param refundId 订单退款对象标识
     * @return 订单退款对象
     */
    OrderRefund getRefund(String refundId);

    /**
     * 申请订单退款。
     *
     * @param refund 订单退款对象
     * @throws OrderRefundException 退款金额小于等于零，退款对象所关联的订单对象的订单项不存在，或者已全额退款
     */
    OrderRefund applyRefund(OrderRefund refund) throws OrderRefundException;

    List<OrderRefund> applyRefunds(List<OrderRefund> refunds) throws OrderRefundException;

    OrderRefund approveRefund(OrderRefund refund) throws OrderRefundException;

    OrderRefund disapproveRefund(OrderRefund refund) throws OrderRefundException;

    OrderRefund reapplyRefund(OrderRefund refund) throws OrderRefundException;

    /**
     * 主动退款操作是 {@link #applyRefund(OrderRefund)} 和 {@link #approveRefund(OrderRefund)}
     * 两个方法的组合。
     *
     * @param refund 订单退款对象
     * @throws OrderRefundException 请查看 {@link #applyRefund(OrderRefund)} 异常原因
     */
    OrderRefund activeRefund(OrderRefund refund) throws OrderRefundException;

    OrderRefund cancelRefund(OrderRefund refund) throws OrderRefundException;

    void succeedRefund(OrderRefund refund) throws OrderRefundException;

    void failRefund(OrderRefund refund) throws OrderRefundException;

    OrderStatus getReviewStatus();

    /**
     * 获得订单是否还可以评论。
     */
    boolean isReviewable();

    /**
     * 创建一个新的订单评论对象。
     */
    OrderReview createReview(String reviewId);

    /**
     * 批量添加商品订单评价。
     *
     * @param reviews 订单评论对象集合
     * @throws OrderReviewException 订单项重复评论
     */
    List<OrderReview> review(List<OrderReview> reviews) throws OrderReviewException;

    List<OrderReview> getReviews();

    int getTotalQuantity();

    BigDecimal getTotalShippingCost();

    BigDecimal getTotalDiscountShippingCost();

    BigDecimal getTotalPrice();

    BigDecimal getTotalDiscountTotalPrice();

    BigDecimal getSubtotalAmount();

    BigDecimal getTotalAmount();

    String getPaymentId();

    void setPaymentId(String paymentId);

    PaymentStatus getPaymentStatus();

    void setPaymentStatus(PaymentStatus paymentStatus);

    PaymentMethod getPaymentMethod();

    void setPaymentMethod(PaymentMethod paymentMethod);

    /**
     * 订单对象扣减库存时所使用的扣减模式。
     *
     * @return 所使用的扣减模式
     */
    InventoryDeduction getInventoryDeduction();

    void setInventoryDeduction(InventoryDeduction inventoryDeduction);

    int getPlacingExpires();

    boolean isPlaced();

    boolean isPayable();

    boolean isPaid();

    boolean isPlacingExpired();

    Date getPlacedTime();

    Date getPlacingExpiredTime();

    Date getPaidTime();

    Date getFulfilledTime();

    Date getShippedTime();

    Date getSignedTime();

    Date getReceivedTime();

    String getCancelReason();

    Date getCancelledTime();

    String getCloseReason();

    Date getClosedTime();

    String getDeclineReason();

    Date getDeclinedTime();

    Date getRefundedTime();

    OrderDiscount createDiscount(String itemId);

    void discount(OrderDiscount discount);

    void discount(List<OrderDiscount> discounts);

    void place(int placingExpires) throws OrderException;

    void pay(OrderPaymentResult result) throws OrderException;

    /**
     * 订单对象打包。
     *
     * @throws OrderException 订单对象已完成打包状态
     */
    void fulfil() throws OrderException;

    String getSignMessage();

    /**
     * 订单对象签收，sign 和 receipt 区别是签收可以是自签收也可以是他人代签收。
     *
     * @throws OrderException 订单对象已完成签收状态
     */
    void sign(String message) throws OrderException;

    /**
     * 确认收货。
     *
     * @throws OrderException 订单对象已完成确认收货状态
     */
    void receipt() throws OrderException;

    OrderRating createRating(OrderRatingType ratingType);

    List<OrderRating> getRatings();

    void rating(List<OrderRating> ratings);

    void cancel(String cancelReason) throws OrderException;

    void close(String closeReason) throws OrderException;

    void decline(String declineReason);

    interface Builder extends ObjectBuilder<Order> {

        Builder customerId(String customerId);

        Builder shippingAddress(Address shippingAddress);

        Builder pay(OrderPaymentResult payment);

        Builder item(OrderItem item);

        Builder item(Function<OrderItem, OrderItem> function);
    }
}
