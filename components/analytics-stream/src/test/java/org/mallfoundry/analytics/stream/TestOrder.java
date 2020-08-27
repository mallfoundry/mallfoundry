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

package org.mallfoundry.analytics.stream;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.inventory.InventoryDeduction;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderException;
import org.mallfoundry.order.OrderId;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrderPaymentResult;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.aftersales.OrderRefundException;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.order.OrderReviewException;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.order.OrderStatus;
import org.mallfoundry.payment.PaymentMethod;
import org.mallfoundry.payment.PaymentStatus;
import org.mallfoundry.shipping.Address;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class TestOrder implements Order {
    private String id;
    private OrderStatus status;
    private String storeId;
    private String storeName;
    private String sourceName;
    private OrderSource source;
    private String customerId;
    private String customerMessage;
    private String staffNotes;
    private Integer staffStars;
    private Address shippingAddress;
    private List<OrderItem> items = new ArrayList<>();
    private List<OrderShipment> shipments = new ArrayList<>();
    private List<OrderRefund> refunds = new ArrayList<>();
    private int shippedItems;
    private OrderStatus refundStatus;
    private String paymentId;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String signMessage;
    private InventoryDeduction inventoryDeduction;
    private String cancelReason;
    private String closeReason;
    private String declineReason;
    private Date placedTime;
    private int placingExpires = 60 * 1000;
    private Date placingExpiredTime;
    private Date paidTime;
    private Date shippedTime;
    private Date fulfilledTime;
    private Date signedTime;
    private Date receivedTime;
    private Date cancelledTime;
    private Date closedTime;
    private Date refundedTime;
    private Date declinedTime;

    @Override
    public boolean isPlacingExpired() {
        return false;
    }

    @Override
    public OrderId toId() {
        return null;
    }

    @Override
    public OrderItem createItem(String itemId) {
        return null;
    }

    @Override
    public void addItem(OrderItem item) {

    }

    @Override
    public OrderItem getItem(String itemId) {
        return null;
    }

    @Override
    public List<OrderItem> getItems(List<String> itemIds) {
        return null;
    }

    @Override
    public OrderShipment createShipment(String shipmentId) {
        return null;
    }

    @Override
    public void addShipment(OrderShipment shipment) {

    }

    @Override
    public OrderShipment getShipment(String id) {
        return null;
    }

    @Override
    public Optional<OrderShipment> findShipment(String id) {
        return Optional.empty();
    }

    @Override
    public List<OrderShipment> getShipments(Set<String> shipmentIds) {
        return null;
    }

    @Override
    public void updateShipment(OrderShipment shipment) {

    }

    @Override
    public void updateShipments(List<OrderShipment> shipments) {

    }

    @Override
    public void removeShipment(OrderShipment shipment) {

    }

    @Override
    public void removeShipments(List<OrderShipment> shipments) {

    }

    @Override
    public OrderRefund createRefund(String refundId) {
        return null;
    }

    @Override
    public Optional<OrderRefund> findRefund(String refundId) {
        return Optional.empty();
    }

    @Override
    public OrderRefund getRefund(String refundId) {
        return null;
    }

    @Override
    public OrderRefund applyRefund(OrderRefund refund) {
        return refund;
    }

    @Override
    public List<OrderRefund> applyRefunds(List<OrderRefund> refunds) throws OrderRefundException {
        return null;
    }

    @Override
    public OrderRefund approveRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    public OrderRefund disapproveRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    public OrderRefund activeRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    public OrderRefund cancelRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    public void succeedRefund(OrderRefund refund) throws OrderRefundException {

    }

    @Override
    public void failRefund(OrderRefund refund) throws OrderRefundException {

    }


    @Override
    public OrderStatus getReviewStatus() {
        return null;
    }

    @Override
    public boolean canReview() {
        return false;
    }

    @Override
    public OrderReview createReview(String reviewId) {
        return null;
    }

    @Override
    public OrderReview addReview(OrderReview review) throws OrderReviewException {
        return null;
    }

    @Override
    public List<OrderReview> addReviews(List<OrderReview> reviews) throws OrderReviewException {
        return null;
    }

    @Override
    public OrderReview getReview(String reviewId) {
        return null;
    }

    @Override
    public List<OrderReview> getReviews() {
        return null;
    }

    @Override
    public int getTotalItems() {
        return 0;
    }

    @Override
    public BigDecimal getTotalDiscountAmount() {
        return null;
    }

    @Override
    public BigDecimal getTotalShippingCost() {
        return null;
    }

    @Override
    public BigDecimal getTotalDiscountShippingCost() {
        return null;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getTotalAmount() {
        return null;
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return null;
    }

    @Override
    public boolean isPlaced() {
        return false;
    }

    @Override
    public boolean canPay() {
        return false;
    }

    @Override
    public boolean isPaid() {
        return false;
    }

    @Override
    public void discounts(Map<String, BigDecimal> amounts) {

    }

    @Override
    public void discountShippingCosts(Map<String, BigDecimal> shippingCosts) {

    }

    @Override
    public void place(int expires) throws OrderException {

    }

    @Override
    public void pay(OrderPaymentResult details) throws OrderException {

    }

    @Override
    public void fulfil() throws OrderException {

    }

    @Override
    public void cancel(String reason) throws OrderException {

    }

    @Override
    public void close(String closeReason) throws OrderException {

    }

    @Override
    public void decline(String declineReason) {

    }

    @Override
    public void sign(String message) throws OrderException {

    }

    @Override
    public void receipt() throws OrderException {

    }

    @Override
    public Builder toBuilder() {
        return null;
    }

    @Override
    public String getTenantId() {
        return null;
    }
}
