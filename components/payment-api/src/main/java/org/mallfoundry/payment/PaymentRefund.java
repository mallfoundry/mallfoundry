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

import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;

public interface PaymentRefund extends ObjectBuilder.ToBuilder<PaymentRefund.Builder> {

    String getId();

    void setId(String id);

    String getPaymentId();

    String getStoreId();

    void setStoreId(String storeId);

    String getOrderId();

    void setOrderId(String orderId);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    PaymentRefundStatus getStatus();

    String getReason();

    void setReason(String reason);

    String getFailReason();

    Date getAppliedTime();

    Date getSucceededTime();

    Date getFailedTime();

    void apply();

    void succeed();

    void fail(String failReason);

    interface Builder extends ObjectBuilder<PaymentRefund> {

        Builder id(String id);

        Builder orderId(String orderId);

        Builder storeId(String storeId);

        Builder amount(int amount);

        Builder amount(double amount);

        Builder amount(BigDecimal amount);

        Builder reason(String reason);
    }
}
