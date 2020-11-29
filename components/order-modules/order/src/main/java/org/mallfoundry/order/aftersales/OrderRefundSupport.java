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

package org.mallfoundry.order.aftersales;

import java.math.BigDecimal;
import java.util.List;

public abstract class OrderRefundSupport extends OrderDisputeSupport implements MutableOrderRefund {

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
        public Builder kind(OrderDisputeKind kind) {
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
        public Builder itemId(String itemId) {
            this.refund.setItemId(itemId);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.refund.setName(name);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.refund.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder amount(BigDecimal amount) {
            this.refund.setAmount(amount);
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
