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

public abstract class OrderExceptions {

    public static OrderException notFound() {
        return new OrderException(OrderMessages.notFound());
    }

    public static OrderException unpaid() {
        return new OrderException(OrderMessages.unpaid());
    }

    public static OrderException notSameCustomer() {
        return new OrderException(OrderMessages.notSameCustomer());
    }

    public static OrderException placingExpired() {
        return new OrderException(OrderMessages.placingExpired());
    }

    public static OrderException notPay() {
        return new OrderException(OrderMessages.notPay());
    }

    public static OrderException notReview() {
        return new OrderException(OrderMessages.notReview());
    }

    public abstract static class Item {
        public static OrderException notFound() {
            return new OrderException(OrderMessages.Item.notFound());
        }

        public static OrderReviewException reviewed(String itemId) {
            return new OrderReviewException(OrderMessages.Item.reviewed(itemId));
        }
    }

    public abstract static class Shipment {
        public static OrderShipmentException notFound() {
            return new OrderShipmentException(OrderMessages.Shipment.notFound());
        }
    }

    public abstract static class Refund {

        public static OrderRefundException notFound() {
            return new OrderRefundException(OrderMessages.Refund.notFound());
        }

        public static OrderRefundException applied() {
            return new OrderRefundException(OrderMessages.Refund.applied());
        }

        public static OrderRefundException notCancel() {
            return new OrderRefundException(OrderMessages.Refund.notCancel());
        }

        public static OrderRefundException approvedOrDisapproved() {
            return new OrderRefundException(OrderMessages.Refund.approvedOrDisapproved());
        }

        public static OrderRefundException completed() {
            return new OrderRefundException(OrderMessages.Refund.completed());
        }

        public static OrderRefundException overApply() {
            return new OrderRefundException(OrderMessages.Refund.overApply());
        }

        public static OrderRefundException applying() {
            return new OrderRefundException(OrderMessages.Refund.overApply());
        }
    }

    public abstract static class Review {
        public static OrderReviewException notFound() {
            return new OrderReviewException("");
        }
    }
}
