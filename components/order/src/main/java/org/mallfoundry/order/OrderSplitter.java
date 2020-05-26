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

package org.mallfoundry.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class OrderSplitter {

    private final List<OrderSplitPolicy> splitters;

    @Autowired(required = false)
    public OrderSplitter(List<OrderSplitPolicy> splitters) {
        this.splitters = Objects.isNull(splitters) ? Collections.emptyList() : splitters;
    }

    public List<Order> splitting(Order order) {
        return splitting(List.of(order));
    }

    public List<Order> splitting(List<Order> orders) {
        for (OrderSplitPolicy splitter : splitters) {
            orders = splitter.splitting(orders);
        }
        return orders;
    }
}
