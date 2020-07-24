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

import org.mallfoundry.util.DecimalUtils;

import java.math.BigDecimal;

public abstract class OrderItemSupport implements MutableOrderItem {

    @Override
    public BigDecimal getTotalPrice() {
        return this.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getTotalPrice().add(this.getShippingCost());
    }

    @Override
    public BigDecimal getTotalAmount() {
        return this.getSubtotalAmount().add(this.getDiscountAmount()).add(this.getDiscountShippingCost());
    }

    private void addRefundingAmount(BigDecimal deltaAmount) {
        this.setRefundingAmount(this.getRefundingAmount().add(deltaAmount));
    }

    private void addRefundedAmount(BigDecimal deltaAmount) {
        this.setRefundedAmount(this.getRefundedAmount().add(deltaAmount));
    }

    /**
     * 判断是否可以申请退款。
     */
    private boolean overApplyRefund(BigDecimal refundAmount) {
        var newRefundAmount = this.getRefundedAmount().add(this.getRefundingAmount()).add(refundAmount);
        return DecimalUtils.greaterThan(newRefundAmount, this.getTotalAmount());
    }

    @Override
    public void applyRefund(BigDecimal refundAmount) {
        if (this.overApplyRefund(refundAmount)) {
            throw OrderExceptions.Refund.overApply();
        }
        this.addRefundingAmount(refundAmount);
    }

    @Override
    public void cancelRefund(BigDecimal failAmount) {
        this.failRefund(failAmount);
    }

    @Override
    public void disapproveRefund(BigDecimal failAmount) {
        this.failRefund(failAmount);
    }

    @Override
    public void succeedRefund(BigDecimal succeedAmount) {
        this.addRefundingAmount(BigDecimal.ZERO.subtract(succeedAmount));
        this.addRefundedAmount(succeedAmount);
    }

    @Override
    public void failRefund(BigDecimal failAmount) {
        this.addRefundingAmount(BigDecimal.ZERO.subtract(failAmount));
    }
}
