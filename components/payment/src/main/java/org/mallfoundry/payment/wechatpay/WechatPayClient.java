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

package org.mallfoundry.payment.wechatpay;

import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentClient;
import org.mallfoundry.payment.PaymentException;
import org.mallfoundry.payment.PaymentNotification;
import org.mallfoundry.payment.PaymentRefund;

public class WechatPayClient implements PaymentClient {

    @Override
    public boolean supportsPayment(Payment payment) {
        return false;
    }

    @Override
    public String createPaymentRedirectUrl(Payment payment) throws PaymentException {
        return null;
    }

    @Override
    public PaymentNotification validateNotification(Object parameters) {
        return null;
    }

    @Override
    public PaymentRefund refundPayment(Payment payment, PaymentRefund refund) {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
