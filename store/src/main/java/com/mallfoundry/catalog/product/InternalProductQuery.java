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

package com.mallfoundry.catalog.product;

import com.mallfoundry.data.PageableSupport;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class InternalProductQuery extends PageableSupport implements ProductQuery {

    private Set<String> ids;

    private String name;

    private String storeId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Set<String> collectionIds;

    private Set<ProductType> types;

    private Set<ProductStatus> statuses;

    private Set<InventoryStatus> inventoryStatuses;
}
