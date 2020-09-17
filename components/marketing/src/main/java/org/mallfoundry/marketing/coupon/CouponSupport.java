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

package org.mallfoundry.marketing.coupon;

import java.math.BigDecimal;
import java.util.Date;

public abstract class CouponSupport implements MutableCoupon {

    @Override
    public void create() {
        this.setStatus(CouponStatus.ISSUING);
        this.setCreatedTime(new Date());
    }

    @Override
    public void pause() {
        this.setStatus(CouponStatus.PAUSED);
    }

    @Override
    public TakeCoupon take(TakeCoupon takeCoupon) {
        if (this.getReceivedCount() >= this.getIssuingCount()) {
            throw new CouponException("");
        }
        this.setReceivedCount(this.getReceivedCount() + 1);
        takeCoupon.take(this);
        return takeCoupon;
    }

    @Override
    public void use(TakeCoupon takeCoupon) {
//        this.setUsesCount(this.getUses() + 1);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final CouponSupport coupon;

        protected BuilderSupport(CouponSupport coupon) {
            this.coupon = coupon;
        }

        @Override
        public Builder id(String id) {
            this.coupon.setId(id);
            return this;
        }

        @Override
        public Builder storeId(String storeId) {
            this.coupon.setStoreId(storeId);
            return this;
        }

        @Override
        public Builder code(String code) {
            this.coupon.setCode(code);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.coupon.setName(name);
            return this;
        }

        @Override
        public Builder description(String description) {
            this.coupon.setDescription(description);
            return this;
        }

        @Override
        public Builder issuingCount(int issuingCount) {
            this.coupon.setIssuingCount(issuingCount);
            return this;
        }

        @Override
        public Builder type(CouponType type) {
            this.coupon.setType(type);
            return this;
        }

        @Override
        public Builder discountAmount(BigDecimal discountAmount) {
            this.coupon.setDiscountAmount(discountAmount);
            return this;
        }

        @Override
        public Builder discountPercent(BigDecimal discountPercent) {
            this.coupon.setDiscountPercent(discountPercent);
            return this;
        }

        @Override
        public Builder discountMinAmount(BigDecimal discountMinAmount) {
            this.coupon.setDiscountMinAmount(discountMinAmount);
            return this;
        }

        @Override
        public Builder discountMaxAmount(BigDecimal discountMaxAmount) {
            this.coupon.setDiscountMaxAmount(discountMaxAmount);
            return this;
        }

        @Override
        public Builder minAmount(BigDecimal minAmount) {
            this.coupon.setMinAmount(minAmount);
            return this;
        }

        @Override
        public Builder maxAmount(BigDecimal maxAmount) {
            this.coupon.setMaxAmount(maxAmount);
            return this;
        }

        @Override
        public Builder startTime(Date startTime) {
            this.coupon.setStartTime(startTime);
            return this;
        }

        @Override
        public Builder endTime(Date endTime) {
            this.coupon.setEndTime(endTime);
            return this;
        }

        @Override
        public Coupon build() {
            return this.coupon;
        }
    }
}
