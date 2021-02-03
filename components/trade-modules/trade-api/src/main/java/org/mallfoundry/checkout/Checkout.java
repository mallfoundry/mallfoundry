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

package org.mallfoundry.checkout;

import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.shipping.Address;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Checkout {

    String getId();

    String getCartId();

    void setCartId(String cartId);

    OrderSource getSource();

    void setSource(OrderSource source);

    Address getShippingAddress();

    void setShippingAddress(Address shippingAddress);

    CheckoutItem createItem();

    void addItem(CheckoutItem item);

    List<CheckoutItem> getItems();

    List<Order> getOrders();

    void setOrders(List<Order> orders);

    BigDecimal getSubtotalAmount();

    Date getCreatedTime();

    void create();
}
