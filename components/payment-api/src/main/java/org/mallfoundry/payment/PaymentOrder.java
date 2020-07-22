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

/**
 * 支付订单对象是对上层系统业务订单的简化，使其只需要设置用于支付的属性。
 *
 * @author Zhi Tang
 */
public interface PaymentOrder extends ObjectBuilder.ToBuilder<PaymentOrder.Builder> {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    BigDecimal getRefundedAmount();

    BigDecimal getRefundingAmount();

    void applyRefund(BigDecimal refundAmount) throws PaymentRefundException;

    void succeedRefund(BigDecimal succeedAmount) throws PaymentRefundException;

    void failRefund(BigDecimal failAmount) throws PaymentRefundException;

    interface Builder extends ObjectBuilder<PaymentOrder> {

        Builder id(String id);

        Builder storeId(String storeId);

        Builder amount(int amount);

        Builder amount(double amount);

        Builder amount(BigDecimal amount);
    }
}
