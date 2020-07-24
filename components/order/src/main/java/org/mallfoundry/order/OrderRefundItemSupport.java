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
import java.util.List;

public abstract class OrderRefundItemSupport implements MutableOrderRefundItem {

    @Override
    public void itemNotReceive() {
        this.setItemStatus(ItemStatus.NOT_RECEIVED);
    }

    @Override
    public void itemReceive() {
        this.setItemStatus(ItemStatus.RECEIVED);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final OrderRefundItemSupport item;

        public BuilderSupport(OrderRefundItemSupport item) {
            this.item = item;
        }

        @Override
        public Builder itemId(String itemId) {
            this.item.setItemId(itemId);
            return this;
        }

        @Override
        public Builder itemNotReceive() {
            this.item.itemNotReceive();
            return this;
        }

        @Override
        public Builder itemReceive() {
            this.item.itemReceive();
            return this;
        }

        @Override
        public Builder itemStatus(ItemStatus status) {
            this.item.setItemStatus(status);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.item.setName(name);
            return this;
        }

        @Override
        public Builder amount(BigDecimal amount) {
            this.item.setAmount(amount);
            return this;
        }

        @Override
        public Builder reason(String reason) {
            this.item.setReason(reason);
            return this;
        }

        @Override
        public Builder notes(String notes) {
            this.item.setNotes(notes);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.item.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder attachments(List<String> attachments) {
            this.item.setAttachments(attachments);
            return this;
        }

        @Override
        public OrderRefundItem build() {
            return this.item;
        }
    }
}
