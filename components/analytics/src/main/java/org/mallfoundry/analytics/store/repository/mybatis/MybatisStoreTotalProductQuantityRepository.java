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

package org.mallfoundry.analytics.store.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mallfoundry.analytics.store.StoreTotalProductQuantity;
import org.mallfoundry.analytics.store.StoreTotalProductQuantityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface MybatisStoreTotalProductQuantityRepository extends StoreTotalProductQuantityRepository {

    StoreTotalProductQuantity selectByStoreId(@Param("storeId") String storeId);

    @Override
    default Optional<StoreTotalProductQuantity> findByStoreId(String storeId) {
        return Optional.ofNullable(this.selectByStoreId(storeId));
    }
}
