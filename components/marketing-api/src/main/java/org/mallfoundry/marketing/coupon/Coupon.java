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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Coupon extends StoreOwnership {

    String getId();

    void setId(String id);

    String getCode();

    void setCode(String code);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    CouponType getType();

    void setType(CouponType type);

    CouponStatus getStatus();

    int getTakeCount();

    int getUsageCount();

    int getUsageLimit();

    void setUsageLimit(int usageLimit);

    int getTakeLimitPerCustomer();

    void setTakeLimitPerCustomer(int takeLimitPerCustomer);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    BigDecimal getMinAmount();

    void setMinAmount(BigDecimal minAmount);

    BigDecimal getMaxAmount();

    void setMaxAmount(BigDecimal maxAmount);

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

    TakeCoupon take(TakeCoupon takeCoupon);

    void use(TakeCoupon takeCoupon);
}
