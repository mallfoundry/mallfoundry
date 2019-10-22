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

package com.mallfoundry.merchant.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Each product can have multiple variations.
 * The product variant is a combination of option values.
 *
 * @author Zhi Tang
 */
@Getter
@Setter
public class ProductVariant implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("retail_price")
    private BigDecimal retailPrice;

    @JsonProperty("market_price")
    private BigDecimal marketPrice;

    @JsonProperty("stock_quantity")
    private int stockQuantity;

    private List<String> options;

    /**
     * The ids for images.
     */
    private List<String> images;

    private short position;

    public static class Builder {

        private ProductVariant variant;

        public Builder() {
            variant = new ProductVariant();
        }

        public Builder retailPrice(double retailPrice) {
            this.variant.setRetailPrice(BigDecimal.valueOf(retailPrice));
            return this;
        }

        public Builder marketPrice(double marketPrice) {
            this.variant.setMarketPrice(BigDecimal.valueOf(marketPrice));
            return this;
        }

        public Builder stockQuantity(int stockQuantity) {
            this.variant.setStockQuantity(stockQuantity);
            return this;
        }

        public Builder options(List<String> options) {
            this.variant.setOptions(options);
            return this;
        }

        public Builder images(List<String> images) {
            this.variant.setImages(images);
            return this;
        }

        public Builder position(short position) {
            this.variant.setPosition(position);
            return this;
        }

        public Builder position(int position) {
            this.variant.setPosition((short) position);
            return this;
        }

        public ProductVariant build() {
            return this.variant;
        }
    }
}
