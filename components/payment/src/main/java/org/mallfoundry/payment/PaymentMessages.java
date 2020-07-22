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

package org.mallfoundry.payment;

import static org.mallfoundry.i18n.MessageHolder.message;

public abstract class PaymentMessages {

    private static final String PAYMENT_NOT_FOUND_MESSAGE_CODE_KEY = "payment.Payment.notFound";

    private static final String PAYMENT_STARTED_MESSAGE_CODE_KEY = "payment.Payment.started";

    private static final String PAYMENT_CANNOT_CAPTURED_MESSAGE_CODE_KEY = "payment.Payment.cannotCaptured";

    private static final String PAYMENT_CANNOT_ADD_ORDER_MESSAGE_CODE_KEY = "payment.Payment.cannotAddOrder";

    private static final String PAYMENT_CANNOT_APPLY_REFUND_MESSAGE_CODE_KEY = "payment.Payment.cannotApplyRefund";

    private static final String PAYMENT_ORDER_NOT_FOUND_MESSAGE_CODE_KEY = "payment.PaymentOrder.notFound";

    private static final String PAYMENT_REFUND_NOT_FOUND_MESSAGE_CODE_KEY = "payment.PaymentRefund.notFound";

    private static final String PAYMENT_REFUND_DUPLICATE_MESSAGE_CODE_KEY = "payment.PaymentRefund.duplicate";

    private static final String PAYMENT_REFUND_OVER_APPLY_MESSAGE_CODE_KEY = "payment.PaymentRefund.overApply";

    public static String notFound() {
        return message(PAYMENT_NOT_FOUND_MESSAGE_CODE_KEY, "Payment does not exist");
    }

    public static String started() {
        return message(PAYMENT_STARTED_MESSAGE_CODE_KEY, "The payment process has started");
    }

    public static String cannotCaptured() {
        return message(PAYMENT_CANNOT_CAPTURED_MESSAGE_CODE_KEY, "The payment process has been completed and cannot be captured");
    }

    public static String cannotAddOrder() {
        return message(PAYMENT_CANNOT_ADD_ORDER_MESSAGE_CODE_KEY, "The payment process has been started and payment order cannot be added");
    }

    public static String cannotApplyRefund() {
        return message(PAYMENT_CANNOT_APPLY_REFUND_MESSAGE_CODE_KEY, "The payment process is not captured and cannot be refunded");
    }

    public abstract static class Order {

        public static String notFound() {
            return message(PAYMENT_ORDER_NOT_FOUND_MESSAGE_CODE_KEY, "Payment order does not exist");
        }
    }

    public abstract static class Refund {
        public static String notFound() {
            return message(PAYMENT_REFUND_NOT_FOUND_MESSAGE_CODE_KEY, "Payment refund does not exist");
        }

        public static String overApply() {
            return message(PAYMENT_REFUND_OVER_APPLY_MESSAGE_CODE_KEY, "Payment refund applications were oversubscribed");
        }

        public static String duplicate() {
            return message(PAYMENT_REFUND_DUPLICATE_MESSAGE_CODE_KEY, "Duplicate payment refund");
        }
    }
}
