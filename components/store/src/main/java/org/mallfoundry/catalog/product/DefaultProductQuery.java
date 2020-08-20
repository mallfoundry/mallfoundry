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

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;
import org.mallfoundry.inventory.InventoryStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Supplier;

@Getter
@Setter
public class DefaultProductQuery extends QuerySupport implements ProductQuery {
    private Set<String> ids;
    private String name;
    private String storeId;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private Set<String> collections;
    private Set<ProductType> types;
    private Set<ProductStatus> statuses;
    private Set<InventoryStatus> inventoryStatuses;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this);
    }

    public static class BuilderSupport extends QueryBuilderSupport<ProductQuery, Builder> implements Builder {

        private final ProductQuery query;

        public BuilderSupport(ProductQuery query) {
            super(query);
            this.query = query;
        }

        public Builder ids(Set<String> ids) {
            this.query.setIds(ids);
            return this;
        }

        public Builder name(String name) {
            this.query.setName(name);
            return this;
        }

        public Builder priceMin(BigDecimal priceMin) {
            this.query.setPriceMin(priceMin);
            return this;
        }

        public Builder priceMax(BigDecimal priceMax) {
            this.query.setPriceMax(priceMax);
            return this;
        }

        public Builder storeId(String storeId) {
            this.query.setStoreId(storeId);
            return this;
        }

        public Builder collections(Set<String> collections) {
            this.query.setCollections(collections);
            return this;
        }

        public Builder types(Set<ProductType> types) {
            this.query.setTypes(types);
            return this;
        }

        public Builder types(Supplier<Set<ProductType>> supplier) {
            return this.types(supplier.get());
        }

        public Builder statuses(Set<ProductStatus> statuses) {
            this.query.setStatuses(statuses);
            return this;
        }

        public Builder statuses(Supplier<Set<ProductStatus>> supplier) {
            return this.statuses(supplier.get());
        }

        public Builder inventoryStatuses(Set<InventoryStatus> statuses) {
            this.query.setInventoryStatuses(statuses);
            return this;
        }

        public Builder inventoryStatuses(Supplier<Set<InventoryStatus>> supplier) {
            return this.inventoryStatuses(supplier.get());
        }

        public ProductQuery build() {
            return this.query;
        }
    }
}
