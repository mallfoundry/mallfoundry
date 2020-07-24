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

import org.apache.commons.collections4.ListUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.mallfoundry.order.OrderRefundStatus.APPLYING;
import static org.mallfoundry.order.OrderRefundStatus.DISAPPROVED;
import static org.mallfoundry.order.OrderRefundStatus.FAILED;
import static org.mallfoundry.order.OrderRefundStatus.PENDING;
import static org.mallfoundry.order.OrderRefundStatus.SUCCEEDED;

public abstract class OrderRefundSupport implements MutableOrderRefund {

    @Override
    public OrderRefundItem createItem(String id) {
        return new DefaultRefundItem(id);
    }

    @Override
    public void addItem(OrderRefundItem item) {
        this.getItems().add(item);
    }

    @Override
    public void addItems(List<OrderRefundItem> items) {
        ListUtils.emptyIfNull(items).forEach(this::addItem);
    }

    public boolean nonIncomplete() {
        return Objects.nonNull(this.getStatus()) && !this.getStatus().isIncomplete();
    }

    public boolean nonApplying() {
        return Objects.nonNull(this.getStatus()) && !this.getStatus().isApplying();
    }

    public boolean nonPending() {
        return Objects.nonNull(this.getStatus()) && !this.getStatus().isPending();
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
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.notCancel();
        }
    }

    @Override
    public void approve() throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.approvedOrDisapproved();
        }
        this.setStatus(PENDING);
        this.setApprovedTime(new Date());
    }

    @Override
    public void disapprove(String disapprovalReason) throws OrderRefundException {
        if (this.nonApplying()) {
            throw OrderExceptions.Refund.approvedOrDisapproved();
        }
        this.setDisapprovalReason(disapprovalReason);
        this.setStatus(DISAPPROVED);
        this.setDisapprovedTime(new Date());
    }

    @Override
    public void succeed() throws OrderRefundException {
        if (this.nonPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setStatus(SUCCEEDED);
        this.setSucceededTime(new Date());
    }

    @Override
    public void fail(String failReason) throws OrderRefundException {
        if (this.nonPending()) {
            throw OrderExceptions.Refund.completed();
        }
        this.setFailReason(failReason);
        this.setStatus(FAILED);
        this.setFailedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final OrderRefundSupport refund;

        protected BuilderSupport(OrderRefundSupport refund) {
            this.refund = refund;
        }

        @Override
        public Builder kind(OrderRefundKind kind) {
            this.refund.setKind(kind);
            return this;
        }


        @Override
        public Builder item(OrderRefundItem item) {
            this.refund.addItem(item);
            return this;
        }

        @Override
        public Builder item(Function<OrderRefund, OrderRefundItem> function) {
            this.refund.addItem(function.apply(this.refund));
            return this;
        }

        @Override
        public Builder items(List<OrderRefundItem> items) {
            this.refund.addItems(items);
            return this;
        }

        @Override
        public Builder items(Function<OrderRefund, List<OrderRefundItem>> function) {
            return this.items(function.apply(this.refund));
        }

        @Override
        public Builder items(Supplier<List<OrderRefundItem>> supplier) {
            return this.items(supplier.get());
        }

        @Override
        public OrderRefund build() {
            return this.refund;
        }
    }
}
