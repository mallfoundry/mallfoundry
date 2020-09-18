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

package org.mallfoundry.edw.order;

import java.math.BigDecimal;

public interface OrderFact {

    String getTenantId();

    void setTenantId(String tenantId);

    String getStoreId();

    void setStoreId(String storeId);

    String getCustomerId();

    void setCustomerId(String customerId);

    Long getPlacedTimeId();

    void setPlacedTimeId(Long placedTimeId);

    Long getPaidTimeId();

    void setPaidTimeId(Long paidTimeId);

    Long getFulfilledTimeId();

    void setFulfilledTimeId(Long fulfilledTimeId);

    Long getShippedTimeId();

    Long getSignedTimeId();

    Long getReceivedTimeId();

    String getId();

    void setId(String id);

    int getTotalQuantity();

    void setTotalQuantity(int totalQuantity);

    BigDecimal getTotalShippingCost();

    void setTotalShippingCost(BigDecimal totalShippingCost);

    BigDecimal getTotalDiscountShippingCost();

    void setTotalDiscountShippingCost(BigDecimal totalDiscountShippingCost);

    BigDecimal getTotalPrice();

    void setTotalPrice(BigDecimal totalPrice);

    BigDecimal getTotalDiscountTotalPrice();

    void setTotalDiscountTotalPrice(BigDecimal totalDiscountTotalPrice);

    BigDecimal getSubtotalAmount();

    void setSubtotalAmount(BigDecimal subtotalAmount);

    BigDecimal getTotalAmount();

    void setTotalAmount(BigDecimal totalAmount);
}
