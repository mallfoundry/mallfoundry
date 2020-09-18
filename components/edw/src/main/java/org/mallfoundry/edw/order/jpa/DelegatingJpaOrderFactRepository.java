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

package org.mallfoundry.edw.order.jpa;

import org.mallfoundry.edw.order.OrderFact;
import org.mallfoundry.edw.order.OrderFactRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;

public class DelegatingJpaOrderFactRepository implements OrderFactRepository {

    private final JpaOrderFactRepository repository;

    public DelegatingJpaOrderFactRepository(JpaOrderFactRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderFact create() {
        return new JpaOrderFact();
    }

    @Override
    public OrderFact save(OrderFact fact) {
        return this.repository.save((JpaOrderFact) fact);
    }

    @Override
    public List<OrderFact> saveAll(List<OrderFact> facts) {
        return CastUtils.cast(this.repository.saveAll(CastUtils.cast(facts)));
    }

    @Override
    public void delete(OrderFact fact) {
        this.repository.delete((JpaOrderFact) fact);
    }
}
