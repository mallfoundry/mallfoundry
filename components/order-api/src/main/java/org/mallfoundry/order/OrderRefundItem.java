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

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.math.BigDecimal;

/**
 * 订单退款项。
 *
 * @author Zhi Tang
 */
public interface OrderRefundItem extends Position, ObjectBuilder.ToBuilder<OrderRefundItem.Builder> {

    String getId();

    void setId(String id);

    String getItemId();

    void setItemId(String itemId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    // 订单项金额
    BigDecimal getItemAmount();

    void setItemAmount(BigDecimal itemAmount);

    boolean isItemShipped();

    void setItemShipped(boolean itemShipped);

    // 订单退款项金额
    BigDecimal getAmount();

    void setAmount(BigDecimal refundAmount);

    interface Builder extends ObjectBuilder<OrderRefundItem> {

        Builder itemId(String itemId);

        Builder name(String name);

        Builder imageUrl(String imageUrl);

        Builder amount(BigDecimal amount);
    }
}
