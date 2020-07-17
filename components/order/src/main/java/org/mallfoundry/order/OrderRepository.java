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

import org.mallfoundry.data.SliceList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order create(String id);

    Order save(Order order);

    List<Order> saveAll(Iterable<Order> orders);

    Optional<Order> findById(String id);

    List<Order> findAllById(Collection<String> ids);

    SliceList<Order> findAll(OrderQuery query);

    long count(OrderQuery query);
}
