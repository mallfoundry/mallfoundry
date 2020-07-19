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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static org.mallfoundry.order.OrderRefundStatus.APPLYING;
import static org.mallfoundry.order.OrderRefundStatus.DISAPPROVED;
import static org.mallfoundry.order.OrderRefundStatus.FAILED;
import static org.mallfoundry.order.OrderRefundStatus.PENDING;
import static org.mallfoundry.order.OrderRefundStatus.SUCCEEDED;

public abstract class OrderRefundSupport implements MutableOrderRefund {

    private boolean nonIncomplete() {
        return Objects.nonNull(this.getStatus());
    }

    @Override
    public boolean isApplying() {
        return this.nonIncomplete() && this.getStatus().isApplying();
    }

    @Override
    public boolean isDisapproved() {
        return this.nonIncomplete() && this.getStatus().isDisapproved();
    }

    @Override
    public boolean isPending() {
        return this.nonIncomplete() && this.getStatus().isPending();
    }

    @Override
    public boolean isSucceeded() {
        return this.nonIncomplete() && this.getStatus().isSucceeded();
    }

    @Override
    public boolean isFailed() {
        return this.nonIncomplete() && this.getStatus().isFailed();
    }

    private void reduceSetTotalAmount() {
        var totalAmount = this.getItems().stream()
                .map(OrderRefundItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.setTotalAmount(totalAmount);
    }

    @Override
    public void apply() throws OrderRefundException {
        if (this.nonIncomplete()) {
            throw OrderExceptions.Refund.applied();
        }
        this.setStatus(APPLYING);
        this.setAppliedTime(new Date());
        this.reduceSetTotalAmount();
    }

    @Override
    public void cancel() throws OrderRefundException {
        if (!this.isApplying()) {
            throw OrderExceptions.Refund.pending();
        }
    }

    @Override
    public void approve() throws OrderRefundException {
        if (!this.isApplying()) {
            throw OrderExceptions.Refund.pending();
        }
        this.setStatus(PENDING);
        this.setApprovedTime(new Date());
    }

    @Override
    public void disapprove(String disapprovedReason) throws OrderRefundException {
        if (!this.isApplying()) {
            throw OrderExceptions.Refund.pending();
        }
        this.setDisapprovedReason(disapprovedReason);
        this.setStatus(DISAPPROVED);
        this.setDisapprovedTime(new Date());
    }

    @Override
    public void succeed() throws OrderRefundException {
        if (!this.isPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setStatus(SUCCEEDED);
        this.setSucceededTime(new Date());
    }

    @Override
    public void fail(String failReason) throws OrderRefundException {
        if (!this.isPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setFailReason(failReason);
        this.setStatus(FAILED);
        this.setFailedTime(new Date());
    }
}
