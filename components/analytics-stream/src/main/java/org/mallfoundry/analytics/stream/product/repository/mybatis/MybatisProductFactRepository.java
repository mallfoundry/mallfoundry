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

package org.mallfoundry.analytics.stream.product.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.dw.ProductFact;
import org.mallfoundry.dw.ProductQuantityFact;
import org.mallfoundry.analytics.stream.product.ProductFactRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Mapper
@Repository
public interface MybatisProductFactRepository extends ProductFactRepository {

    ProductFact selectById(String id);

    void insert(@Param("product") ProductFact fact);

    void update(@Param("product") ProductFact fact);

    @Override
    List<ProductQuantityFact> countAll(@Param("product") ProductFact fact);

    @Override
    default ProductFact save(ProductFact fact) {
        if (Objects.isNull(this.selectById(fact.getId()))) {
            this.insert(fact);
        } else {
            this.update(fact);
        }
        return this.selectById(fact.getId());
    }
}