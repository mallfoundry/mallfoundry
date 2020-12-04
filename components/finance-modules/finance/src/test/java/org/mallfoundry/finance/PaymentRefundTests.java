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

package org.mallfoundry.finance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mallfoundry.finance.repository.jpa.JpaPayment;
import org.mallfoundry.test.StaticTest;

import java.math.BigDecimal;

import static org.apache.commons.lang3.function.Failable.asConsumer;

@StaticTest
public class PaymentRefundTests {

    @Test
    public void testApplyRefundAndOverRefund() {
        var payment = new JpaPayment()
                .toBuilder()
                .order(asConsumer(order -> order.toBuilder().id("o1").storeId("s1").amount(100).build()))
                .order(asConsumer(order -> order.toBuilder().id("o2").storeId("s1").amount(100).build()))
                .build();
        payment.start();
        payment.capture();
        var refund = payment.createRefund("r1").toBuilder().orderId("o1").amount(50).reason("No").build();
        payment.applyRefund(refund);
        var refund2 = payment.createRefund("r2").toBuilder().orderId("o1").amount(50).reason("No").build();
        payment.applyRefund(refund2);
        var refund3 = payment.createRefund("r3").toBuilder().orderId("o1").amount(50).reason("No").build();
        Assertions.assertThatExceptionOfType(PaymentRefundException.class)
                .isThrownBy(() -> payment.applyRefund(refund3))
                .withMessage(PaymentMessages.Refund.overApply());
    }

    @Test
    public void testApplyRefundWithNotFoundOrder() {
        var payment = new JpaPayment()
                .toBuilder()
                .order(asConsumer(order -> order.toBuilder().id("o1").storeId("s1").amount(100).build()))
                .build();
        payment.start();
        payment.capture();
        var refund = payment.createRefund("r1").toBuilder().orderId("o2").amount(50).reason("No").build();
        Assertions.assertThatExceptionOfType(PaymentOrderException.class)
                .isThrownBy(() -> payment.applyRefund(refund))
                .withMessage(PaymentMessages.Order.notFound());
    }

    @Test
    public void testApplyRefundWithSucceedRefundWithOverRefund() {
        var payment = new JpaPayment();
        var o1 = payment.createOrder("o1").toBuilder().storeId("s1").amount(100).build();
        payment.addOrder(o1);
        payment.start();
        payment.capture();

        // Test r1
        var r1 = payment.createRefund("r1").toBuilder().orderId("o1").amount(50).reason("No").build();
        payment.applyRefund(r1);
        Assertions.assertThat(o1.getRefundingAmount()).isEqualTo(r1.getAmount());
        payment.succeedRefund("r1");
        Assertions.assertThat(o1.getRefundingAmount()).isEqualTo(BigDecimal.ZERO);
        Assertions.assertThat(o1.getRefundedAmount()).isEqualTo(r1.getAmount());

        // Test r2
        var r2 = payment.createRefund("r2").toBuilder().orderId("o1").amount(50).reason("No").build();
        payment.applyRefund(r2);
        Assertions.assertThat(o1.getRefundingAmount()).isEqualTo(BigDecimal.valueOf(50));
        payment.succeedRefund("r2");
        Assertions.assertThat(o1.getRefundingAmount()).isEqualTo(BigDecimal.ZERO);
        Assertions.assertThat(o1.getRefundedAmount()).isEqualTo(BigDecimal.valueOf(100));

        var r3 = payment.createRefund("r3").toBuilder().orderId("o1").amount(50).reason("No").build();
        Assertions.assertThatExceptionOfType(PaymentRefundException.class)
                .isThrownBy(() -> payment.applyRefund(r3))
                .withMessage(PaymentMessages.Refund.overApply());
    }
}
