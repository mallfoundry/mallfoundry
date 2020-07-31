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

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.util.DecimalUtils;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public void review() {
        this.setReviewed(true);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final OrderItem item;

        protected BuilderSupport(OrderItem item) {
            this.item = item;
        }

        @Override
        public Builder id(String id) {
            this.item.setId(id);
            return this;
        }

        @Override
        public Builder storeId(String storeId) {
            this.item.setStoreId(storeId);
            return this;
        }

        @Override
        public Builder productId(String productId) {
            this.item.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.item.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder optionSelections(List<OptionSelection> optionSelections) {
            this.item.setOptionSelections(optionSelections);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.item.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.item.setName(name);
            return this;
        }

        @Override
        public Builder quantity(int quantity) {
            this.item.setQuantity(quantity);
            return this;
        }

        @Override
        public Builder price(BigDecimal price) {
            this.item.setPrice(price);
            return this;
        }

        @Override
        public OrderItem build() {
            return this.item;
        }
    }
}
