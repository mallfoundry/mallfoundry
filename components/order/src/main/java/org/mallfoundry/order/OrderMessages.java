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

import java.util.List;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class OrderMessages {

    private static final String ORDER_NOT_FOUND_MESSAGE_CODE_KEY = "order.Order.notFound";

    private static final String ORDER_UNPAID_MESSAGE_CODE_KEY = "order.Order.unpaid";

    private static final String ORDER_PLACING_EXPIRED_MESSAGE_CODE_KEY = "order.Order.placingExpired";

    private static final String ORDER_NOT_PAY_MESSAGE_CODE_KEY = "order.Order.notPay";

    private static final String ORDER_NOT_REVIEW_MESSAGE_CODE_KEY = "order.Order.notReview";

    private static final String ORDER_FULL_REFUND_REASON_MESSAGE_CODE_KEY = "order.Order.fullRefundReason";

    private static final String ORDER_NOT_SAME_CUSTOMER_MESSAGE_CODE_KEY = "order.Order.notSameCustomer";

    private static final String ORDER_ITEM_NOT_FOUND_MESSAGE_CODE_KEY = "order.OrderItem.notFound";

    private static final String ORDER_ITEM_REVIEWED_MESSAGE_CODE_KEY = "order.OrderItem.reviewed";

    private static final String ORDER_SOURCE_NOT_NULL_MESSAGE_CODE_KEY = "order.OrderSource.notNull";

    private static final String ORDER_SHIPMENT_NOT_FOUND_MESSAGE_CODE_KEY = "order.OrderShipment.notFound";

    private static final String ORDER_REFUND_NOT_FOUND_MESSAGE_CODE_KEY = "order.OrderRefund.notFound";

    private static final String ORDER_REFUND_APPLIED_MESSAGE_CODE_KEY = "order.OrderRefund.applied";

    private static final String ORDER_REFUND_APPROVED_OR_DISAPPROVED_MESSAGE_CODE_KEY = "order.OrderRefund.approvedOrDisapproved";

    private static final String ORDER_REFUND_NOT_CANCEL_MESSAGE_CODE_KEY = "order.OrderRefund.notCancel";

    private static final String ORDER_REFUND_COMPLETED_MESSAGE_CODE_KEY = "order.OrderRefund.completed";

    private static final String ORDER_REFUND_OVER_APPLY_MESSAGE_CODE_KEY = "order.OrderRefund.overApply";

    public static String notFound() {
        return message(ORDER_NOT_FOUND_MESSAGE_CODE_KEY, "Order does not exist");
    }

    public static String unpaid() {
        return message(ORDER_UNPAID_MESSAGE_CODE_KEY, "Order is unpaid");
    }

    public static String notPay() {
        return message(ORDER_NOT_PAY_MESSAGE_CODE_KEY, "The order is not payable");
    }

    public static String notReview() {
        return message(ORDER_NOT_REVIEW_MESSAGE_CODE_KEY, "The order is not reviewed");
    }

    public static String fullRefundReason() {
        return message(ORDER_FULL_REFUND_REASON_MESSAGE_CODE_KEY, "A full refund on the order");
    }

    public static String placingExpired() {
        return message(ORDER_PLACING_EXPIRED_MESSAGE_CODE_KEY, "The order time expired");
    }

    public static String notSameCustomer() {
        return message(ORDER_NOT_SAME_CUSTOMER_MESSAGE_CODE_KEY, "Not a collection of orders from the same customer");
    }

    public abstract static class Source {
        public static String notNull() {
            return message(ORDER_SOURCE_NOT_NULL_MESSAGE_CODE_KEY, "Order does not exist");
        }
    }

    public abstract static class Item {
        public static String notFound() {
            return message(ORDER_ITEM_NOT_FOUND_MESSAGE_CODE_KEY, "Order item does not exist");
        }

        public static String reviewed(String itemId) {
            return message(ORDER_ITEM_REVIEWED_MESSAGE_CODE_KEY, List.of(itemId),
                    String.format("Order item(%s) has been reviewed", itemId));
        }
    }

    public abstract static class Shipment {

        public static String notFound() {
            return message(ORDER_SHIPMENT_NOT_FOUND_MESSAGE_CODE_KEY, "Order shipment does not exist");
        }
    }

    public abstract static class Refund {

        public static String notFound() {
            return message(ORDER_REFUND_NOT_FOUND_MESSAGE_CODE_KEY, "Order refund does not exist");
        }

        public static String applied() {
            return message(ORDER_REFUND_APPLIED_MESSAGE_CODE_KEY, "Order refund has been applied");
        }

        public static String approvedOrDisapproved() {
            return message(ORDER_REFUND_APPROVED_OR_DISAPPROVED_MESSAGE_CODE_KEY, "Order refund has been approved or disapproved");
        }

        public static String notCancel() {
            return message(ORDER_REFUND_NOT_CANCEL_MESSAGE_CODE_KEY, "Order refunds cannot be cancelled");
        }

        public static String completed() {
            return message(ORDER_REFUND_COMPLETED_MESSAGE_CODE_KEY, "Order refund has been completed");
        }

        public static String overApply() {
            return message(ORDER_REFUND_OVER_APPLY_MESSAGE_CODE_KEY, "Order refund applications were oversubscribed");
        }
    }
}
