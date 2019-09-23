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

package com.github.shop.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductSKU implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("retail_price")
    private BigDecimal retailPrice;

    @JsonProperty("market_price")
    private BigDecimal marketPrice;

    @JsonProperty("stock_quantity")
    private int stockQuantity;

    private List<Integer> specs;

    private short index;

    public static class Builder {

        private ProductSKU productSku;

        public Builder() {
            productSku = new ProductSKU();
        }

        public Builder retailPrice(double retailPrice) {
            this.productSku.setRetailPrice(BigDecimal.valueOf(retailPrice));
            return this;
        }

        public Builder marketPrice(double marketPrice) {
            this.productSku.setMarketPrice(BigDecimal.valueOf(marketPrice));
            return this;
        }

        public Builder stockQuantity(int stockQuantity) {
            this.productSku.setStockQuantity(stockQuantity);
            return this;
        }

        public Builder specs(List<Integer> specs) {
            this.productSku.setSpecs(specs);
            return this;
        }

        public Builder index(short index) {
            this.productSku.setIndex(index);
            return this;
        }

        public Builder index(int index) {
            this.productSku.setIndex((short) index);
            return this;
        }

        public ProductSKU build() {
            return this.productSku;
        }
    }
}
