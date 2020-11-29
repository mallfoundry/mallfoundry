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

import org.mallfoundry.store.StoreOwnership;
import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Coupon extends StoreOwnership, ObjectBuilder.ToBuilder<Coupon.Builder> {

    String getId();

    void setId(String id);

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    CouponStatus getStatus();

    /**
     * 客户已收到的优惠券数量。
     */
    int getReceivedCount();

    int getUsedCount();

    int getIssuingCount();

    void setIssuingCount(int issuingCount);

    /**
     * 每个客户可以获得优惠券的数量。
     */
    int getTakeLimitPerCustomer();

    void setTakeLimitPerCustomer(int takeLimitPerCustomer);

    CouponType getType();

    void setType(CouponType type);

    BigDecimal getDiscountAmount();

    void setDiscountAmount(BigDecimal discountAmount);

    BigDecimal getDiscountPercent();

    void setDiscountPercent(BigDecimal discountPercent);

    BigDecimal getDiscountMinAmount();

    void setDiscountMinAmount(BigDecimal discountMinAmount);

    BigDecimal getDiscountMaxAmount();

    void setDiscountMaxAmount(BigDecimal discountMaxAmount);

    BigDecimal getMinAmount();

    void setMinAmount(BigDecimal minAmount);

    BigDecimal getMaxAmount();

    void setMaxAmount(BigDecimal maxAmount);

    /*void getPaidAmount();*/

    List<String> getProducts();

    void setProducts(List<String> products);

    List<String> getExcludedProducts();

    void setExcludedProducts(List<String> excludedProducts);

    List<String> getCollections();

    void setCollections(List<String> collections);

    List<String> getExcludedCollections();

    void setExcludedCollections(List<String> excludedCollections);

    Date getStartTime();

    void setStartTime(Date startTime);

    Date getEndTime();

    void setEndTime(Date endTime);

    Date getCreatedTime();

    void create();

    void pause();

    TakeCoupon take(TakeCoupon takeCoupon);

    void use(TakeCoupon takeCoupon);

    interface Builder extends ObjectBuilder<Coupon> {

        Builder id(String id);

        Builder storeId(String storeId);

        Builder code(String code);

        Builder name(String name);

        Builder description(String description);

        Builder issuingCount(int issuingCount);

        Builder type(CouponType type);

        Builder discountAmount(BigDecimal discountAmount);

        Builder discountPercent(BigDecimal discountPercent);

        Builder discountMinAmount(BigDecimal discountMinAmount);

        Builder discountMaxAmount(BigDecimal discountMaxAmount);

        Builder minAmount(BigDecimal minAmount);

        Builder maxAmount(BigDecimal maxAmount);

        Builder startTime(Date startTime);

        Builder endTime(Date endTime);
    }
}
