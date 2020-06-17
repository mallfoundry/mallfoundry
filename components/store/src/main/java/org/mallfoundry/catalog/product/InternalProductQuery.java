/*
 * Copyright 2019 the original author or authors.
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
public class InternalProductQuery extends QuerySupport implements ProductQuery {

    private Set<String> ids;

    private String name;

    private String storeId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

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

        public Builder minPrice(BigDecimal minPrice) {
            this.query.setMinPrice(minPrice);
            return this;
        }

        public Builder maxPrice(BigDecimal maxPrice) {
            this.query.setMaxPrice(maxPrice);
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
