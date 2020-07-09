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

package org.mallfoundry.analytics.stream.order.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.dw.OrderItemFact;
import org.mallfoundry.dw.OrderQuantityFact;
import org.mallfoundry.analytics.stream.order.OrderItemFactRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface MybatisOrderItemFactRepository extends OrderItemFactRepository {

    void insert(@Param("item") OrderItemFact item);

    int update(@Param("item") OrderItemFact item);

    List<OrderItemFact> findAllById(@Param("ids") Iterable<String> ids);

    @Override
    default List<OrderItemFact> saveAll(Collection<OrderItemFact> items) {
        List<String> ids = new ArrayList<>();
        for (var item : items) {
            if (this.update(item) == 0) {
                this.insert(item);
            }
            ids.add(item.getId());
        }
        return this.findAllById(ids);
    }

    @Override
    List<OrderQuantityFact> countAll(@Param("items") List<OrderItemFact> items);
}
