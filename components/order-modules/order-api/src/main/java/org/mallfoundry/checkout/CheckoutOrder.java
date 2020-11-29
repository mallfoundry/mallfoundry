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

package org.mallfoundry.checkout;

import org.mallfoundry.inventory.InventoryDeduction;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderDiscount;
import org.mallfoundry.order.OrderException;
import org.mallfoundry.order.OrderId;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrderPaymentResult;
import org.mallfoundry.order.OrderRating;
import org.mallfoundry.order.OrderRatingType;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.order.OrderReviewException;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.order.OrderStatus;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.aftersales.OrderRefundException;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.finance.PaymentMethod;
import org.mallfoundry.finance.PaymentStatus;
import org.mallfoundry.shipping.Address;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CheckoutOrder extends Order {

    @Override
    default OrderId toId() {
        return null;
    }

    @Override
    default Address getShippingAddress() {
        return null;
    }

    @Override
    default void setShippingAddress(Address shippingAddress) {

    }

    @Override
    default String getStoreName() {
        return null;
    }

    @Override
    default void setStoreName(String storeName) {

    }

    @Override
    default OrderStatus getStatus() {
        return null;
    }

    @Override
    default String getStaffNotes() {
        return null;
    }

    @Override
    default void setStaffNotes(String staffNotes) {

    }

    @Override
    default Integer getStaffStars() {
        return null;
    }

    @Override
    default void setStaffStars(Integer staffStars) {

    }

    @Override
    default String getSourceName() {
        return null;
    }

    @Override
    default void setSourceName(String sourceName) {

    }

    @Override
    default OrderSource getSource() {
        return null;
    }

    @Override
    default void setSource(OrderSource source) {

    }

    @Override
    default OrderItem createItem(String itemId) {
        return null;
    }

    @Override
    default void addItem(OrderItem item) {

    }

    @Override
    default OrderItem getItem(String itemId) {
        return null;
    }

    @Override
    default List<OrderItem> getItems(List<String> itemIds) {
        return null;
    }

    @Override
    default List<OrderItem> getItems() {
        return null;
    }

    @Override
    default void setItems(List<OrderItem> items) {

    }

    @Override
    default int getTotalQuantity() {
        return 0;
    }

    @Override
    default int getItemsSize() {
        return 0;
    }

    @Override
    default OrderShipment createShipment(String shipmentId) {
        return null;
    }

    @Override
    default void addShipment(OrderShipment shipment) {

    }

    @Override
    default List<OrderShipment> getShipments() {
        return null;
    }

    @Override
    default Optional<OrderShipment> findShipment(String shipmentId) {
        return Optional.empty();
    }

    @Override
    default OrderShipment getShipment(String shipmentId) {
        return null;
    }

    @Override
    default List<OrderShipment> getShipments(Set<String> shipmentIds) {
        return null;
    }

    @Override
    default void updateShipment(OrderShipment shipment) {

    }

    @Override
    default void updateShipments(List<OrderShipment> shipments) {

    }

    @Override
    default void removeShipment(OrderShipment shipment) {

    }

    @Override
    default void removeShipments(List<OrderShipment> shipments) {

    }

    @Override
    default int getShippedItems() {
        return 0;
    }

    @Override
    default OrderStatus getDisputeStatus() {
        return null;
    }

    @Override
    default OrderRefund createRefund(String refundId) {
        return null;
    }

    @Override
    default List<OrderRefund> getRefunds() {
        return null;
    }

    @Override
    default Optional<OrderRefund> findRefund(String refundId) {
        return Optional.empty();
    }

    @Override
    default OrderRefund getRefund(String refundId) {
        return null;
    }

    @Override
    default OrderRefund applyRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    default List<OrderRefund> applyRefunds(List<OrderRefund> refunds) throws OrderRefundException {
        return null;
    }

    @Override
    default OrderRefund approveRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    default OrderRefund disapproveRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    default OrderRefund reapplyRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    default OrderRefund activeRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    default OrderRefund cancelRefund(OrderRefund refund) throws OrderRefundException {
        return null;
    }

    @Override
    default void succeedRefund(OrderRefund refund) throws OrderRefundException {

    }

    @Override
    default void failRefund(OrderRefund refund) throws OrderRefundException {

    }

    @Override
    default OrderStatus getReviewStatus() {
        return null;
    }

    @Override
    default boolean isReviewable() {
        return false;
    }

    @Override
    default OrderReview createReview(String reviewId) {
        return null;
    }

    @Override
    default List<OrderReview> review(List<OrderReview> reviews) throws OrderReviewException {
        return null;
    }

    @Override
    default List<OrderReview> getReviews() {
        return null;
    }

    @Override
    default BigDecimal getTotalDiscountTotalPrice() {
        return null;
    }

    @Override
    default BigDecimal getTotalShippingCost() {
        return null;
    }

    @Override
    default BigDecimal getTotalDiscountShippingCost() {
        return null;
    }

    @Override
    default BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    default BigDecimal getTotalAmount() {
        return null;
    }

    @Override
    default BigDecimal getSubtotalAmount() {
        return null;
    }

    @Override
    default String getPaymentId() {
        return null;
    }

    @Override
    default void setPaymentId(String paymentId) {

    }

    @Override
    default PaymentStatus getPaymentStatus() {
        return null;
    }

    @Override
    default void setPaymentStatus(PaymentStatus paymentStatus) {

    }

    @Override
    default PaymentMethod getPaymentMethod() {
        return null;
    }

    @Override
    default void setPaymentMethod(PaymentMethod paymentMethod) {

    }

    @Override
    default InventoryDeduction getInventoryDeduction() {
        return null;
    }

    @Override
    default void setInventoryDeduction(InventoryDeduction inventoryDeduction) {

    }

    @Override
    default int getPlacingExpires() {
        return 0;
    }

    @Override
    default boolean isPlaced() {
        return false;
    }

    @Override
    default boolean isPayable() {
        return false;
    }

    @Override
    default boolean isPaid() {
        return false;
    }

    @Override
    default boolean isPlacingExpired() {
        return false;
    }

    @Override
    default Date getPlacedTime() {
        return null;
    }

    @Override
    default Date getPlacingExpiredTime() {
        return null;
    }

    @Override
    default Date getPaidTime() {
        return null;
    }

    @Override
    default Date getFulfilledTime() {
        return null;
    }

    @Override
    default Date getShippedTime() {
        return null;
    }

    @Override
    default Date getSignedTime() {
        return null;
    }

    @Override
    default Date getReceivedTime() {
        return null;
    }

    @Override
    default String getCancelReason() {
        return null;
    }

    @Override
    default Date getCancelledTime() {
        return null;
    }

    @Override
    default String getCloseReason() {
        return null;
    }

    @Override
    default Date getClosedTime() {
        return null;
    }

    @Override
    default String getDeclineReason() {
        return null;
    }

    @Override
    default Date getDeclinedTime() {
        return null;
    }

    @Override
    default Date getRefundedTime() {
        return null;
    }

    @Override
    default OrderDiscount createDiscount(String itemId) {
        return null;
    }

    @Override
    default void discount(OrderDiscount discount) {

    }

    @Override
    default void discount(List<OrderDiscount> discounts) {

    }

    @Override
    default void place(int placingExpires) throws OrderException {

    }

    @Override
    default void pay(OrderPaymentResult result) throws OrderException {

    }

    @Override
    default void fulfil() throws OrderException {

    }

    @Override
    default String getSignMessage() {
        return null;
    }

    @Override
    default void sign(String message) throws OrderException {

    }

    @Override
    default void receipt() throws OrderException {

    }

    @Override
    default OrderRating createRating(OrderRatingType ratingType) {
        return null;
    }

    @Override
    default List<OrderRating> getRatings() {
        return null;
    }

    @Override
    default void rating(List<OrderRating> ratings) {

    }

    @Override
    default void cancel(String cancelReason) throws OrderException {

    }

    @Override
    default void close(String closeReason) throws OrderException {

    }

    @Override
    default void decline(String declineReason) {

    }

    @Override
    default String getCustomerId() {
        return null;
    }

    @Override
    default String getStoreId() {
        return null;
    }

    @Override
    default String getTenantId() {
        return null;
    }

    @Override
    default Builder toBuilder() {
        return null;
    }
}
