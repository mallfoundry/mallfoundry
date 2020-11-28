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

import org.mallfoundry.finance.PaymentException;
import org.mallfoundry.finance.PaymentOrderException;
import org.mallfoundry.finance.PaymentRefundException;

public abstract class PaymentExceptions {

    public static PaymentException notFound() {
        return new PaymentException(PaymentMessages.started());
    }

    public static PaymentException started() {
        return new PaymentException(PaymentMessages.started());
    }

    public static PaymentException cannotApplyRefund() {
        return new PaymentException(PaymentMessages.cannotApplyRefund());
    }

    public static PaymentException cannotCaptured() {
        return new PaymentException(PaymentMessages.cannotCaptured());
    }

    public static PaymentException cannotAddOrder() {
        return new PaymentException(PaymentMessages.cannotAddOrder());
    }

    public abstract static class Order {
        public static PaymentOrderException notFound() {
            return new PaymentOrderException(PaymentMessages.Order.notFound());
        }
    }

    public abstract static class Refund {
        public static PaymentRefundException notFound() {
            return new PaymentRefundException(PaymentMessages.Refund.notFound());
        }

        public static PaymentRefundException overApply() {
            return new PaymentRefundException(PaymentMessages.Refund.overApply());
        }

        public static PaymentRefundException duplicate() {
            return new PaymentRefundException(PaymentMessages.Refund.duplicate());
        }
    }
}
