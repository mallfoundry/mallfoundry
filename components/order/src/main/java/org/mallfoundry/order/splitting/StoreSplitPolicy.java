/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
