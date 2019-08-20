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

package com.github.shop.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class ProductItem {

    public ProductItem() {

    }

    public ProductItem(BigDecimal retailPrice, BigDecimal marketPrice, int stockQuantity,
                       List<Integer> specs, List<ProductImage> images, List<ProductVideo> videos, short sortOrder) {
        this.retailPrice = retailPrice;
        this.marketPrice = marketPrice;
        this.stockQuantity = stockQuantity;
        this.specs = specs;
        this.images = images;
        this.videos = videos;
        this.sortOrder = sortOrder;
    }

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @JsonProperty("product_id")
    private Long productId;

    @Getter
    @Setter
    @JsonProperty("retail_price")
    private BigDecimal retailPrice;

    @Getter
    @Setter
    @JsonProperty("market_price")
    private BigDecimal marketPrice;

    @Getter
    @Setter
    @JsonProperty("stock_quantity")
    private int stockQuantity;

    @Getter
    @Setter
    private List<Integer> specs;

    @Getter
    @Setter
    private List<ProductImage> images;

    @Getter
    @Setter
    private List<ProductVideo> videos;

    @Getter
    @Setter
    @JsonProperty("sort_order")
    private short sortOrder;
}
