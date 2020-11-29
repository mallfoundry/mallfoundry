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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class DefaultPaymentOrder extends PaymentOrderSupport {
    private String id;
    private String storeId;
    private BigDecimal amount;
    private BigDecimal refundedAmount;
    private BigDecimal refundingAmount;

    public DefaultPaymentOrder(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return Objects.requireNonNullElse(this.amount, BigDecimal.ZERO);
    }

    public BigDecimal getRefundedAmount() {
        return Objects.requireNonNullElse(this.refundedAmount, BigDecimal.ZERO);
    }

    public BigDecimal getRefundingAmount() {
        return Objects.requireNonNullElse(this.refundingAmount, BigDecimal.ZERO);
    }
}
