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

import org.mallfoundry.util.Positions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.mallfoundry.order.OrderRefundStatus.APPLYING;
import static org.mallfoundry.order.OrderRefundStatus.DISAPPROVED;
import static org.mallfoundry.order.OrderRefundStatus.FAILED;
import static org.mallfoundry.order.OrderRefundStatus.INCOMPLETE;
import static org.mallfoundry.order.OrderRefundStatus.PENDING;
import static org.mallfoundry.order.OrderRefundStatus.SUCCEEDED;

public abstract class OrderRefundSupport implements MutableOrderRefund {

    public boolean nonIncomplete() {
        return !INCOMPLETE.equals(this.getStatus());
    }

    public boolean nonApplying() {
        return !APPLYING.equals(this.getStatus());
    }

    public boolean nonPending() {
        return !PENDING.equals(this.getStatus());
    }

    @Override
    public void addItem(OrderRefundItem item) {
        this.getItems().add(item);
        Positions.sort(this.getItems());
    }

    @Override
    public void addItems(List<OrderRefundItem> items) {
        this.getItems().addAll(items);
        Positions.sort(this.getItems());
    }

    @Override
    public void itemNotReceive() {
        this.setItemStatus(ItemStatus.NOT_RECEIVED);
    }

    @Override
    public void itemReceive() {
        this.setItemStatus(ItemStatus.RECEIVED);
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
        public Builder kind(RefundKind kind) {
            this.refund.setKind(kind);
            return this;
        }

        @Override
        public Builder reason(String reason) {
            this.refund.setReason(reason);
            return this;
        }

        @Override
        public Builder itemStatus(ItemStatus itemStatus) {
            if (ItemStatus.RECEIVED.equals(itemStatus)) {
                return this.itemReceive();
            } else if (ItemStatus.NOT_RECEIVED.equals(itemStatus)) {
                return this.itemNotReceive();
            }
            return this;
        }

        @Override
        public Builder itemNotReceive() {
            this.refund.itemNotReceive();
            return this;
        }

        @Override
        public Builder itemReceive() {
            this.refund.itemReceive();
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
            this.refund.addItems(function.apply(this.refund));
            return this;
        }

        @Override
        public Builder items(Supplier<List<OrderRefundItem>> supplier) {
            this.refund.addItems(supplier.get());
            return this;
        }

        @Override
        public Builder disapprovalReason(String disapprovalReason) {
            this.refund.setDisapprovalReason(disapprovalReason);
            return this;
        }

        @Override
        public Builder failReason(String failReason) {
            this.refund.setFailReason(failReason);
            return this;
        }

        @Override
        public Builder notes(String notes) {
            this.refund.setNotes(notes);
            return this;
        }

        @Override
        public Builder attachments(List<String> attachments) {
            this.refund.setAttachments(attachments);
            return this;
        }

        @Override
        public OrderRefund build() {
            return this.refund;
        }
    }
}
