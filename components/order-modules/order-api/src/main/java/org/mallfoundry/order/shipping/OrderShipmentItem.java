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

package org.mallfoundry.order.shipping;

import org.mallfoundry.util.ObjectBuilder;

/**
 * 订单物流信息项对象，此对象与订单项对象进行关联，主要描述对订单项的发货物流信息。
 *
 * @author Zhi Tang
 */
public interface OrderShipmentItem extends ObjectBuilder.ToBuilder<OrderShipmentItem.Builder> {

    /**
     * 返回物流信息项标识。
     */
    String getId();

    void setId(String id);

    /**
     * 返回订单项标识。
     */
    String getItemId();

    void setItemId(String itemId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantity();

    void setQuantity(int quantity);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    interface Builder extends ObjectBuilder<OrderShipmentItem> {

        Builder itemId(String itemId);

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder quantity(int quantity);

        Builder name(String name);

        Builder imageUrl(String imageUrl);
    }
}
