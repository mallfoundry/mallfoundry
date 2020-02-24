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

package com.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallfoundry.store.StoreId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Long id;

    @JsonProperty("store_id")
    @Embedded
    private StoreId storeId;

    @JsonProperty("product_id")
    @Column(name = "product_id_")
    private Long productId;

    @JsonProperty("variant_id")
    @Column(name = "variant_id_")
    private Long variantId;

    @Column(name = "title_")
    private String title;

    @Column(name = "options_")
    private String options;

    @JsonProperty("image_url")
    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "quantity_")
    private int quantity;

    @Column(name = "price_")
    private BigDecimal price;

    @JsonIgnore
    @Transient
    public BigDecimal getTotalAmount() {
        return this.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private OrderItem item;

        public Builder() {
            item = new OrderItem();
        }

        public Builder storeId(StoreId storeId) {
            this.item.setStoreId(storeId);
            return this;
        }

        public Builder productId(Long productId) {
            this.item.setProductId(productId);
            return this;
        }

        public Builder variantId(Long variantId) {
            this.item.setVariantId(variantId);
            return this;
        }

        public Builder title(String title) {
            this.item.setTitle(title);
            return this;
        }

        public Builder quantity(int quantity) {
            this.item.setQuantity(quantity);
            return this;
        }

        public Builder price(BigDecimal price) {
            this.item.setPrice(price);
            return this;
        }

        public OrderItem build() {
            return this.item;
        }
    }

}
