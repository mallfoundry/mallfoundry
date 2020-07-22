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

import org.apache.commons.lang3.Functions;
import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

@StandaloneTest
public class PaymentServiceTests {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testStartPayment() {
        var payment = this.paymentService.createPayment("id")
                .toBuilder()
                .payerId("p1")
                .payer("per")
                .instrument(Functions.asConsumer(instrument -> instrument.toBuilder().type(PaymentMethod.ALIPAY).build()))
                .order(Functions.asConsumer(order -> order.toBuilder().id("o1").storeId("s1").amount(1).build()))
                .order(Functions.asConsumer(order -> order.toBuilder().id("o2").storeId("s1").amount(1.2).build()))
                .order(Functions.asConsumer(order -> order.toBuilder().id("o3").storeId("s1").amount(1.1).build()))
                .build();
        this.paymentService.startPayment(payment);
    }

    @Test
    public void testRefundPayment() {
        var refund = this.paymentService.createPayment("2")
                .createRefund("r4")
                .toBuilder().amount(0.2).reason("1").orderId("o2").build();
        this.paymentService.refundPayment("2", refund);
    }
}
