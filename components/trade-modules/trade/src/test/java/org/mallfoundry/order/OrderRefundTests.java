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


import org.junit.jupiter.api.Test;
import org.mallfoundry.order.repository.jpa.JpaOrder;
import org.mallfoundry.finance.PaymentMethodType;
import org.mallfoundry.finance.PaymentStatus;
import org.mallfoundry.test.StaticTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@StaticTest
public class OrderRefundTests {

    @Test
    public void testUnpaidThenApplyRefund() {
        var order = new JpaOrder();
        var refund = order.createRefund("1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        assertThatExceptionOfType(OrderException.class)
                .isThrownBy(() -> order.applyRefund(refund))
                .withMessage(OrderExceptions.unpaid().getMessage());
    }

    @Test
    public void testPaidAndItemNotFoundThenApplyRefund() {
        var order = new JpaOrder();
        order.pay(new DefaultOrderPaymentResult("1", PaymentMethodType.ALIPAY, PaymentStatus.CAPTURED));
        var refund = order.createRefund("1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        assertThatExceptionOfType(OrderException.class)
                .isThrownBy(() -> order.applyRefund(refund))
                .withMessage(OrderExceptions.Item.notFound().getMessage());
    }

    @Test
    public void testPaidAndItemThenApplyRefundAmountGreaterThanItemTotalAmount() {
        var order = new JpaOrder()
                .toBuilder()
                .item(item -> item.toBuilder().id("i1").price(BigDecimal.valueOf(1)).quantity(2).build())
                .pay(new DefaultOrderPaymentResult("1", PaymentMethodType.ALIPAY, PaymentStatus.CAPTURED))
                .build();
        var refund = order.createRefund("1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        assertThatExceptionOfType(OrderException.class)
                .isThrownBy(() -> order.applyRefund(refund))
                .withMessage(OrderExceptions.Refund.overApply().getMessage());
    }

    @Test
    public void testPaidAndItemThenApplyRefundTwoAmountEqualsItemTotalAmount() {
        var order = new JpaOrder()
                .toBuilder()
                .item(item -> item.toBuilder().id("i1").price(BigDecimal.valueOf(10)).quantity(2).build())
                .pay(new DefaultOrderPaymentResult("1", PaymentMethodType.ALIPAY, PaymentStatus.CAPTURED))
                .build();
        var refund = order.createRefund("1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        order.applyRefund(refund);
    }

    @Test
    public void testPaidAndItemThenApplyRefundTwoAmountGreaterThanItemTotalAmount() {
        var order = new JpaOrder()
                .toBuilder()
                .item(item -> item.toBuilder().id("12").price(BigDecimal.valueOf(1)).quantity(2).build())
                .pay(new DefaultOrderPaymentResult("1", PaymentMethodType.ALIPAY, PaymentStatus.CAPTURED))
                .build();
        var refund = order.createRefund("1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        assertThatExceptionOfType(OrderException.class)
                .isThrownBy(() -> order.applyRefund(refund))
                .withMessage(OrderExceptions.Refund.overApply().getMessage());
    }

}
