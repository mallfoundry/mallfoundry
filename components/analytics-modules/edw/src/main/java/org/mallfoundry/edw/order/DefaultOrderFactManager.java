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

import java.util.List;

public class DefaultOrderFactManager implements OrderFactManager {

    private final OrderFactRepository orderFactRepository;

    public DefaultOrderFactManager(OrderFactRepository orderFactRepository) {
        this.orderFactRepository = orderFactRepository;
    }

    @Override
    public OrderFactKey createOrderFactKey() {
        return new ImmutableOrderFactKey();
    }

    @Override
    public OrderFact createOrderFact(OrderFactKey factKey) {
        return this.orderFactRepository.create(factKey);
    }

    private OrderFact getOrderFact(OrderFactKey key) {
        return this.orderFactRepository.findByKey(key)
                .orElseGet(() -> this.orderFactRepository.create(key));
    }

    @Override
    public List<OrderFact> saveOrderFacts(List<OrderFact> facts) {
        return this.orderFactRepository.saveAll(facts);
    }
}
