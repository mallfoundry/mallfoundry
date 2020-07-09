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

package org.mallfoundry.order.splitting;

import org.mallfoundry.order.InternalOrder;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrderSplitPolicy;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StoreSplitPolicy implements OrderSplitPolicy {

    private Function<List<OrderItem>, Order> assign0(Order source) {
        var order = new InternalOrder();
        BeanUtils.copyProperties(source, order, "items");
        return (items) -> {
            order.setItems(items);
            return order;
        };
    }

    @Override
    public List<Order> splitting(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream()
                        // group by store id
                        .collect(Collectors.groupingBy(OrderItem::getStoreId))
                        .values().stream()
                        .map(this.assign0(order))
                )
                .collect(Collectors.toList());
    }
}
