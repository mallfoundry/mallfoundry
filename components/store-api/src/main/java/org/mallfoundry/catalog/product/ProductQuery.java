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

package org.mallfoundry.catalog.product;

import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;
import org.mallfoundry.inventory.InventoryStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Supplier;

public interface ProductQuery extends Query {

    Set<String> getIds();

    void setIds(Set<String> ids);

    String getName();

    void setName(String name);

    String getStoreId();

    void setStoreId(String storeId);

    BigDecimal getPriceMin();

    void setPriceMin(BigDecimal minPrice);

    BigDecimal getPriceMax();

    void setPriceMax(BigDecimal maxPrice);

    Set<String> getCollections();

    void setCollections(Set<String> collections);

    Set<ProductType> getTypes();

    void setTypes(Set<ProductType> types);

    Set<ProductStatus> getStatuses();

    void setStatuses(Set<ProductStatus> statuses);

    Set<InventoryStatus> getInventoryStatuses();

    void setInventoryStatuses(Set<InventoryStatus> statuses);

    Builder toBuilder();

    interface Builder extends QueryBuilder<ProductQuery, Builder> {
        Builder ids(Set<String> ids);

        Builder name(String name);

        Builder priceMin(BigDecimal priceMin);

        Builder priceMax(BigDecimal priceMax);

        Builder storeId(String storeId);

        Builder collections(Set<String> collections);

        Builder types(Set<ProductType> types);

        Builder types(Supplier<Set<ProductType>> supplier);

        Builder statuses(Set<ProductStatus> statuses);

        Builder statuses(Supplier<Set<ProductStatus>> supplier);

        Builder inventoryStatuses(Set<InventoryStatus> statuses);

        Builder inventoryStatuses(Supplier<Set<InventoryStatus>> supplier);
    }
}
