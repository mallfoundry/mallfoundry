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

package com.mallfoundry.store.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Each product can have multiple variations.
 * The product variant is a combination of option values.
 *
 * @author Zhi Tang
 */
@Getter
@Setter
@Entity
@Table(name = "store_product_variant")
public class ProductVariant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Long id;

    @Column(name = "retail_price_")
    @JsonProperty("retail_price")
    private BigDecimal retailPrice;

    @Column(name = "market_price_")
    @JsonProperty("market_price")
    private BigDecimal marketPrice;

    @Column(name = "stock_quantity_")
    @JsonProperty("stock_quantity")
    private int stockQuantity;

    @ElementCollection
    @CollectionTable(name = "store_product_variant_option")
    private List<String> options = new ArrayList<>();

    /**
     * The ids for images.
     */
    @ElementCollection
    @CollectionTable(name = "store_product_variant_image")
    private List<String> images;

    @Column(name = "position_")
    private Integer position;

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

        public Builder position(int position) {
            this.variant.setPosition(position);
            return this;
        }

        public Builder options(List<String> options) {
            this.variant.setOptions(new ArrayList<>(options));
            return this;
        }

        public Builder images(List<String> images) {
            this.variant.setImages(new ArrayList<>(images));
            return this;
        }

        public ProductVariant build() {
            return this.variant;
        }
    }
}
