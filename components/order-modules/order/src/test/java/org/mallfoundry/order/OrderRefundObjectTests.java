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
import org.mallfoundry.order.aftersales.OrderRefundException;
import org.mallfoundry.order.aftersales.repository.jpa.JpaOrderRefund;
import org.mallfoundry.test.StaticTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@StaticTest
public class OrderRefundObjectTests {

    @Test
    public void testApplyRefund() {
        var refund = new JpaOrderRefund("0", "1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        refund.apply();
    }

    @Test
    public void testNotApplyAndApproveRefund() {
        var refund = new JpaOrderRefund("0", "1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        assertThatExceptionOfType(OrderRefundException.class)
                .isThrownBy(refund::approve)
                .withMessage(OrderMessages.Refund.approvedOrDisapproved());
    }

    @Test
    public void testCancelRefund() {
        var refund = new JpaOrderRefund("0", "1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        refund.cancel();
    }

    @Test
    public void testApproveAndCancelRefund() {
        var refund = new JpaOrderRefund("0", "1").toBuilder()
                .itemNotReceive()
                .itemId("i1").amount(BigDecimal.valueOf(10))
                .build();
        assertThatExceptionOfType(OrderRefundException.class)
                .isThrownBy(() -> {
                    refund.apply();
                    refund.approve();
                    refund.cancel();
                })
                .withMessage(OrderMessages.Refund.notCancel());
    }
}
