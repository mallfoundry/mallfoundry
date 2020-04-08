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

package com.mallfoundry.order.splitting;

import com.mallfoundry.order.InternalOrder;
import com.mallfoundry.order.OrderItem;
import com.mallfoundry.order.OrderSplitPolicy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreSplitPolicy implements OrderSplitPolicy {

    @Override
    public List<InternalOrder> splitting(List<InternalOrder> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream()
                        // group by store id
                        .collect(Collectors.groupingBy(OrderItem::getStoreId))
                        .values().stream()
                        .map(items -> this.assign(InternalOrder.builder().items(items).build(), order))
                )
                .collect(Collectors.toList());
    }
}
