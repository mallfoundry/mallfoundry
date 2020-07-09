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

package org.mallfoundry.discount;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Coupon {

    String getId();

    String getStoreId();

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    String getCode();

    void setCode(String code);

    CouponStatus getStatus();

    int getUses();

    int getMaxUses();

    void setMaxUses(int maxUses);

    int getMaxUsesPerCustomer();

    void setMaxUsesPerCustomer(int maxUsesPerCustomer);

    UsesLimit getUsesLimit();

    void setUsesLimit(UsesLimit limit);

    int getExpires();

    void setExpires(int expires);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    BigDecimal getMinAmount();

    void setMinAmount(BigDecimal minAmount);

    BigDecimal getMaxAmount();

    void setMaxAmount(BigDecimal maxAmount);

    List<String> getProducts();

    void setProducts(List<String> products);

    List<String> getExcludedProducts();

    void setExcludedProducts(List<String> products);

    List<String> getCollections();

    void setCollections(List<String> collections);

    List<String> getExcludedCollections();

    void setExcludedCollections(List<String> collections);

    Date getCreatedTime();

//    BigDecimal calculateDiscountAmount();
}
