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

package org.mallfoundry.order.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderQuery;
import org.mallfoundry.order.OrderRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DelegatingJpaOrderRepository implements OrderRepository {

    private final JpaOrderRepository repository;

    public DelegatingJpaOrderRepository(JpaOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(String id) {
        return new JpaOrder(id);
    }

    @Override
    public Order save(Order order) {
        return this.repository.save(JpaOrder.of(order));
    }

    @Override
    public List<Order> saveAll(Collection<Order> orders) {
        return CastUtils.cast(
                this.repository.saveAll(
                        orders.stream().map(JpaOrder::of).collect(Collectors.toUnmodifiableList())));
    }

    @Override
    public Optional<Order> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public List<Order> findAllById(Collection<String> ids) {
        return CastUtils.cast(this.repository.findAllById(ids));
    }

    @Override
    public SliceList<Order> findAll(OrderQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(query.getPage()).limit(query.getLimit())
                .totalSize(page.getTotalElements()).cast();
    }

    @Override
    public long count(OrderQuery query) {
        return this.repository.count(query);
    }
}
